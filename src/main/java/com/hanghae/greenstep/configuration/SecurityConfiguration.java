package com.hanghae.greenstep.configuration;


import com.hanghae.greenstep.jwt.AccessDeniedHandlerException;
import com.hanghae.greenstep.jwt.AuthenticationEntryPointException;
import com.hanghae.greenstep.jwt.TokenProvider;
import com.hanghae.greenstep.jwt.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SecurityConfiguration {

    @Value("${jwt.secret}")
    String SECRET_KEY;
    private final TokenProvider tokenProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationEntryPointException authenticationEntryPointException;
    private final AccessDeniedHandlerException accessDeniedHandlerException;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors();

        http.csrf().disable()

                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPointException)
                .accessDeniedHandler(accessDeniedHandlerException)

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers()
                .frameOptions().sameOrigin()

                .and()
                .authorizeRequests()
                .antMatchers("/users/**").permitAll()
                .antMatchers("/admin-login").permitAll()
                .antMatchers("admin/**").hasRole("ADMIN")
                .antMatchers("admin/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().permitAll()

                .and()
                .apply(new JwtSecurityConfiguration(SECRET_KEY, tokenProvider, userDetailsService))

                .and().logout()
                .logoutUrl("/logout")  /* 로그아웃 url*/
                .logoutSuccessUrl("/")  /* 로그아웃 성공시 이동할 url */
                .invalidateHttpSession(true)  /*로그아웃시 세션 제거*/
                .deleteCookies("JSESSIONID")  /*쿠키 제거*/
                .clearAuthentication(true)    /*권한정보 제거*/
                .permitAll()
                .and().sessionManagement()
                .maximumSessions(1) /* session 허용 갯수 */
                .expiredUrl("/") /* session 만료시 이동 페이지*/
                .maxSessionsPreventsLogin(true); /* 동일한 사용자 로그인시 x, false 일 경우 기존 사용자 session 종료*/



        return http.build();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }

}
