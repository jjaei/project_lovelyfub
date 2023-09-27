package example.domain.user.controller;

import example.domain.user.entity.User;
import example.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class UserController {

	private final UserRepository userRepository;

	@GetMapping("/user")
	public JSONObject oauthLoginInfo(Principal principal) {
		JSONObject obj = new JSONObject();
		try {
			String email = principal.getName();
			Optional<User> optionalUser = userRepository.findByEmail(email);
			if (optionalUser.isPresent()) {
				User users = optionalUser.get();
				obj.put("success", true);
				obj.put("name", users.getName());
				obj.put("email", users.getEmail());
				obj.put("nickname", users.getNickname());
				obj.put("picture", users.getPicture());
				obj.put("sns_link", users.getSns_link());
				obj.put("id", users.getId());
			}
			return obj;
		} catch (NullPointerException e) {
			obj.put("success", false);
			return obj;
		}
	}

	@PutMapping("/userInfo")
	public ResponseEntity<?> loginHandler(@RequestBody User user, Principal principal) {

		String email = principal.getName();
		Optional<User> optionalUser = userRepository.findByEmail(email);

		if (optionalUser.isPresent()) {
			User updateUser = optionalUser.get();
			updateUser.setSns_link(user.getSns_link());
			userRepository.save(updateUser);

			return ResponseEntity.ok("User Info Updated Successfully");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	}
}
