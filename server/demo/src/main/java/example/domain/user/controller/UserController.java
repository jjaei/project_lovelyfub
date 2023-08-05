package example.domain.user.controller;

import example.domain.likes.entity.Likes;
import example.domain.likes.service.LikesService;
import example.domain.store.entity.Store;
import example.domain.store.repository.StoreRepository;
import example.domain.user.entity.User;
import example.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

	private final StoreRepository storeRepository;
	private final LikesService likesService;
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
				obj.put("id", users.getId());
			}
			return obj;
		} catch (NullPointerException e) {
			obj.put("success", false);
			return obj;
		}
	}

	@GetMapping("/mypage")
	public ResponseEntity<Map<String, Object>> myPage(@RequestParam String userid, String email){

		Map<String, Object> responseData = new HashMap<>();
		Optional<User> userOptional = userRepository.findByEmail(email);

		if (userOptional.isPresent()) {
			User user = userOptional.get();
			responseData.put("name", user.getName());
			responseData.put("nickname", user.getNickname());
			responseData.put("email", user.getEmail());
			responseData.put("picture", user.getPicture());

			List<Likes> likeStore = likesService.getAllLikes();
			List<Store> likeStores = new ArrayList<>();

			for (Likes likesStore : likeStore) {
				Integer store_id = likesStore.getStore().getStoreid();
				System.out.println(store_id);
				Optional<Store> store = storeRepository.findByStoreid(store_id);

				if (store.isPresent()) {
					likeStores.add(store.get());
				}
			}
			responseData.put("likeStores", likeStores);
		}
		// 200 OK 상태 코드와 responseData를 JSON 형태로 반환
		return ResponseEntity.ok(responseData);
	}
}