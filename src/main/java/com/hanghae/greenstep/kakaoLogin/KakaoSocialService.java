package com.hanghae.greenstep.kakaoLogin;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.greenstep.exception.CustomException;
import com.hanghae.greenstep.exception.ErrorCode;
import com.hanghae.greenstep.jwt.RefreshTokenRepository;
import com.hanghae.greenstep.jwt.TokenDto;
import com.hanghae.greenstep.jwt.TokenProvider;
import com.hanghae.greenstep.jwt.UserDetailsImpl;
import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.member.MemberRepository;
import com.hanghae.greenstep.shared.Check;
import com.hanghae.greenstep.shared.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.UUID;

import static com.hanghae.greenstep.shared.Authority.ROLE_MEMBER;

@RequiredArgsConstructor
@Service
@Slf4j
public class KakaoSocialService {
    @Value("${kakao.client_id}")
    String kakaoClientId;
    @Value("${kakao.redirect_uri}")
    String RedirectURI;

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final Check check;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenDto kakaoLogin(String code) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(code);
        // 2. 토큰으로 카카오 API 호출
        KakaoMemberInfoDto kakaoMemberInfo = getKakaoUserInfo(accessToken);
        // DB 에 중복된 Kakao Id 가 있는지 확인
        Long kakaoId = kakaoMemberInfo.getId();
        Member kakaoUser = memberRepository.findByKakaoId(kakaoId).orElse(null);
        boolean newComer = false;

        if (kakaoUser == null) {
            // 회원가입
            // username: kakao nickname
            String nickname = kakaoMemberInfo.getNickname();

            // password: random UUID
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);

            // email: kakao email
            String email = kakaoMemberInfo.getEmail();
            String profileImage = kakaoMemberInfo.getProfilePhoto();
            // role: 일반 사용자
            kakaoUser = new Member(kakaoId, email, "이름을 입력해주세요", ROLE_MEMBER, nickname,  encodedPassword, profileImage, "kakao", true);
            memberRepository.save(kakaoUser);
        }
        if(Objects.equals(kakaoUser.getName(),"이름을 입력해주세요")){
            newComer =true;
        }

        // 4. 강제 kakao로그인 처리
        UserDetails userDetails = new UserDetailsImpl(kakaoUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Member member = memberRepository.findByKakaoId(kakaoId).orElseThrow(
                () -> new CustomException(ErrorCode.INVALID_MEMBER_INFO)
        );
        return tokenProvider.generateTokenDto(member, newComer, accessToken);
    }

    private String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoClientId);
        body.add("redirect_uri", RedirectURI);
        body.add("code", code);
        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    public KakaoMemberInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();
        String profilePhoto = jsonNode.get("properties").get("profile_image").asText();

        return new KakaoMemberInfoDto(id, nickname, email, profilePhoto);
    }

    public LoginResponseDto loginInfo(TokenDto tokenDto) {
        return LoginResponseDto.builder()
                .memberId(tokenDto.getMember().getId())
                .nickname(tokenDto.getMember().getNickname())
                .profilePhoto(tokenDto.getMember().getProfilePhoto())
                .newComer(tokenDto.getNewComer())
                .build();
    }


    @Transactional
    public void kakaoLogout(HttpServletRequest request) throws JsonProcessingException {
        Member member = check.accessTokenCheck(request);
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = kakaoTokenHeaderMaker(request);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/authorize?client_id="+kakaoClientId+"&redirect_uri="+RedirectURI+"&response_type=code",
                HttpMethod.GET,
                kakaoTokenRequest,
                String.class
        );
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        if(Objects.equals(member.getKakaoId(), id)) refreshTokenRepository.deleteByMember(member);
    }


    @Transactional
    public void deleteMemberInfo(HttpServletRequest request) throws JsonProcessingException {
        Member member = check.accessTokenCheck(request);
        Long memberId = member.getId();
        memberRepository.deleteById(memberId);
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = kakaoTokenHeaderMaker(request);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v1/user/unlink",
                HttpMethod.GET,
                kakaoTokenRequest,
                String.class
        );
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long kakaoId = jsonNode.get("id").asLong();
        if (Objects.equals(member.getKakaoId(), kakaoId)) {
            refreshTokenRepository.deleteByMember(member);
        } else throw new CustomException(ErrorCode.INVALID_TOKEN);
    }

    public HttpEntity<MultiValueMap<String, String>> kakaoTokenHeaderMaker(HttpServletRequest request){
        String accessToken = request.getHeader("Kakao_Authorization");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        // HTTP 요청 보내기
        return new HttpEntity<>(headers);
    }
}

