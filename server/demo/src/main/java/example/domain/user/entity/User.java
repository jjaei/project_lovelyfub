package example.domain.user.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@AllArgsConstructor
@Entity
@Getter
@Builder
@Component
public class User{
	
	public User(){}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String email;
	
	@Column(name = "picture")
	private String picture;
	
	@Column(nullable = false)
	private String nickname;
	
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
	

	public User update(String name, String email, String picture) {
		this.name = name;
		this.picture = picture;
		
		return this;
	}
	
	public String getRoleKey() {
		return this.role.getKey();
	}
}
