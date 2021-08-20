package com.hyerijang.book.springboot.config.auth.dto;

import com.hyerijang.book.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    //SessionUser 에는 인증 된 사용자 정보만 필요하므로 아래 세 필드만 선언.
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}