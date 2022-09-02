package com.hanghae.greenstep.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column(nullable = false,length = 60)
    private String name;

    @Column
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

}
