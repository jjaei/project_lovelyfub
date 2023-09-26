package example.domain.user.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Component
public class User{

	public User(){}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String email;

	@Column(name = "picture")
	private String picture;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String gender;

	@Column(nullable = false)
	private String birthyear;

	@Column(nullable = false)
	private String birthday;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	private String sns_link;

	@Builder.Default
	private Long lovelyCount = 0l;

	public User update(String name, String email, String picture) {
		this.name = name;
		this.picture = picture;
		this.email = email;

		return this;
	}

	public String getRoleKey() {
		return this.role.getKey();
	}

}
