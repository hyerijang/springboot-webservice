package com.hyerijang.book.springboot.config.auth;

import com.hyerijang.book.springboot.config.auth.dto.OAuthAttributes;
import com.hyerijang.book.springboot.config.auth.dto.SessionUser;
import com.hyerijang.book.springboot.domain.user.User;
import com.hyerijang.book.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 현재 로그인 진행 중인 서비스를 구분하는 코드 (구글,네이버 ...)

        // 로그인 진행시 키가 되는 필드값 (PK)
        // 구글은 지원하지만 네이버, 카카오는 지원 x
        // 구글의 기본 코드는 "sub"
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        //OAuth2UserService 를 통해 가져온 OAuth2User 의  attribute 를 담을 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        //SessionUser : 세션에 사용자 정보를 저장하기 위한 Dto 클래스
        // 기존에 있는 User 클래스를 사용하지 않고 SessionUser 라는 클래스를 따로 만든 이유?
        //   User 클래스를 그대로 사용하면 직렬화를 구현하지 않았다는 에러가 뜬다.
        //   User 가 엔티티이기 때문에 직렬화는 무리.
        //   엔티티는 다른 엔티티와 관계를 맺을 가능성이 높기 때문에 (@OneToMany, @ManyToOne) 직렬화 대상에 자식들까지 포함되어 성능 이슈, 부수효과가 발생할 가능성 높음
        //   때문에 직렬화 기능을 갖는 세션 Dto를 추가로 만드는 것이 추후의 유지보수에 더 좋다.

        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
