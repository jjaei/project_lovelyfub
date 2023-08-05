package example.domain.user.service;


import example.domain.user.entity.User;
import example.domain.user.mapper.OAuthAttributes;
import example.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService <OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
				.getUserInfoEndpoint().getUserNameAttributeName();

		// OAuth2UserService
		OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
		User user = saveOrUpdate(attributes);

		return new DefaultOAuth2User(
				Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
				attributes.getAttributes(),
				"email");
	}

	// 유저 생성 및 수정 서비스 로직
	private User saveOrUpdate(OAuthAttributes attributes) {
		Optional<User> optionalUser = userRepository.findByEmail(attributes.getEmail());
		return optionalUser.orElseGet(() -> userRepository.save(optionalUser.map(
						entity -> entity.update(attributes.getName(), attributes.getEmail(), attributes.getPicture()))
				.orElse(attributes.toEntity())));
	}
}