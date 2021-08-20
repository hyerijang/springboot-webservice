package com.hyerijang.book.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    //소셜 로그인으로 반환 되는 값 중 이메일을 통해 이미 새성된 사용자인지 신규 유저인기 확인 하기 위한 메소드
    Optional<User> findByEmail(String Email);
}
