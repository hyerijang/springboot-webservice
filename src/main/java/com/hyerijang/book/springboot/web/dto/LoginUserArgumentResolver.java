package com.hyerijang.book.springboot.web.dto;

import com.hyerijang.book.springboot.config.auth.dto.SessionUser;
import com.hyerijang.book.springboot.web.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter) { //조건에 맞는 파라미터인지 확인하는 메소드
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null; // @LoginUser이 붙은 파라미터이고
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType()); // User 클래스이면
        return isLoginUserAnnotation && isUserClass; //true 리턴
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 파라미터에 전달할 객체를 생성
        // 여기서는 세션에서 객체를 가져온다.
        return httpSession.getAttribute("user");
    }
}
