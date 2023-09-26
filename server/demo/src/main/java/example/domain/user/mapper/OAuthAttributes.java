package example.domain.user.mapper;

import example.domain.user.entity.Role;
import example.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name,
                           String nickname, String email, String picture, String gender,
                           String birthyear, String birthday) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.picture = picture;
        this.gender = gender;
        this.birthyear = birthyear;
        this.birthday = birthday;
    }

    private final Map<String, Object> attributes;
    private final String nameAttributeKey;
    private final String name;
    private final String nickname;
    private final String email;
    private final String picture;
    private final String gender;
    private final String birthyear;
    private final String birthday;


    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }
        return ofNaver(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .nickname((String) response.get("nickname"))
                .gender((String) response.get("gender"))
                .birthyear((String) response.get("birthyear"))
                .birthday((String) response.get("birthday"))
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
                .gender(gender)
                .birthyear(birthyear)
                .birthday(birthday)
                .role(Role.USER)
                .build();
    }
}