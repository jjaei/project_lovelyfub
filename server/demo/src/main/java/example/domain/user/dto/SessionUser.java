package example.domain.user.dto;

import example.domain.user.entity.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

	// 인증 사용자 정보만 담는 클래스
	private String name;
	private String nickname;
	private String email;
	private String picture;
	private String sns_link;

	public SessionUser(User user) {
		this.name = user.getName();
		this.nickname = user.getNickname();
		this.email = user.getEmail();
		this.picture = user.getPicture();
		this.sns_link = getSns_link();
	}
}