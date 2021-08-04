package com.hyerijang.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
//@SpringBootTest 어노테이션은 기본적으로 서블릿 서버를 실행하지는 않는다.
//WebEnvironment 속성을 사용하면 컨테이너가 서블릿 환경에서 작동되도록 할 수 있다. 기본 설정은 NONE이다.(서버 실행 안 함)
//랜덤한 포트로 지정 되는 것 로그에서 확인 가능. Tomcat started on port(s): ~~
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IndexControllerTest {


    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void 메인페이지_로딩() {
        //given
        //when
        String body = this.restTemplate.getForObject("/", String.class);
        //then
        assertThat(body).contains("스프링부트로 시작하는 웹 서비스");

    }
}