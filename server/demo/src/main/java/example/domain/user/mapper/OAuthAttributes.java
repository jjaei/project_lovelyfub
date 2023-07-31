package example.domain.user.mapper;

import example.domain.user.entity.Role;
import example.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@Builder
@RequiredArgsConstructor
public class OAuthAttributes {
	
	private final Map<String, Object> attributes;
	private final String nameAttributeKey;
	private final String name;
	private final String nickname;
	private final String email;
	private final String picture;

	
	 public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
	        if("naver".equals(registrationId)) {
	            return ofNaver("id", attributes);
	        }
	        
	        return OAuthAttributes.builder().build();
	    }
		
	private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
		Map<String, Object> response = (Map<String, Object>) attributes.get("response");
			
		return OAuthAttributes.builder()
				.name((String) response.get("name"))
				.email((String) response.get("email"))
				.picture((String) response.get("profile_image"))
				.nickname((String) response.get("nickname"))
				.attributes(response)
				.nameAttributeKey(userNameAttributeName)
				.build();
		}
		
	public User toEntity() {
		return User.builder()
				.name(name)
				.email(email)
				.picture(picture)
				.nickname(nickname)
				.role(Role.GUEST)
				.build();
	}	
}
