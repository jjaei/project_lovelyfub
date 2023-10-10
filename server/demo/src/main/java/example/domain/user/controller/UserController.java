package example.domain.user.controller;

import example.domain.user.dto.UserProfileResponse;
import example.domain.user.entity.User;
import example.domain.user.repository.UserRepository;
import example.domain.user.service.UserService;
import example.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class UserController {

	private final UserRepository userRepository;
	private final UserService userService;

	@GetMapping("/user")
	public User myProfile(Principal principal) {
		User user = userService.oauthLoginInfo(principal);
		if (user != null) {
			return user;
		} else {
			throw new NotFoundException("로그인 유저를 찾을 수 없습니다.");
		}
	}

	@PatchMapping("/userInfo")
	public User updateUserInfo(@RequestBody User updateUser, Principal principal) {
		return userService.updateUserInfo(principal, updateUser);
	}

	@GetMapping("/userProfile/{userId}")
	public UserProfileResponse userProfile(@PathVariable Long userId) {
		return userService.getUserProfile(userId);
	}
}
