package com.hyerijang.book.springboot.domain.user;

import com.hyerijang.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column
    private String picture;

    @Enumerated(EnumType.STRING) //JPA로 Enum 값을 어떤 형태로 저장할지 결정 (기본은 숫자). 우리는 문자열로 저장할 것임.
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture ){
        this.name = name;
        this.picture = picture;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }

}
