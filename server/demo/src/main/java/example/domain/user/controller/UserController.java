package example.domain.user.controller;

import example.domain.likes.entity.Likes;
import example.domain.likes.service.LikesService;
import example.domain.store.entity.Store;
import example.domain.store.repository.StoreRepository;
import example.domain.user.entity.User;
import example.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RequiredArgsConstructor
@RestController
public class UserController {
	private final StoreRepository storeRepository;
	private final LikesService likesService;
	private final UserRepository userRepository;

	@GetMapping("/user")
	public JSONObject oauthLoginInfo(@AuthenticationPrincipal OAuth2User user) {
		JSONObject obj = new JSONObject();
		try {
			String email = user.getAttribute("email");
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
	public ResponseEntity<Map<String, Object>> myPage(@AuthenticationPrincipal OAuth2User user){
		JSONObject obj = new JSONObject();

		String name = user.getAttribute("name");
		String nickname = user.getAttribute("nickname");
		String email = user.getAttribute("email");
		String picture = user.getAttribute("picture");

		if(user == null) {
			// 로그인이 되어있지 않으면 401 Unauthorized 응답을 반환하도록 설정
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} else {
			Map<String, Object> attributes = user.getAttributes();
			Map<String, Object> responseData = new HashMap<>();

			responseData.put("name",attributes.get("name"));
			responseData.put("nickname",attributes.get("nickname"));
			responseData.put("email",attributes.get("email"));
			responseData.put("id",attributes.get("id"));

			List<Likes> likeStore = likesService.getAllLikes();
			List<Store> likeStores = new ArrayList<>();

			for(Likes likesStore : likeStore){
				if(attributes.get("email").equals(likesStore.getUser().getEmail())){
					Integer store_id = likesStore.getStore().getStoreid();
					System.out.println(store_id);
					Optional<Store> store = storeRepository.findByStoreid(store_id);

					if(store.isPresent()){
						likeStores.add(store.get());
					}
				}
			}
			responseData.put("likeStores",likeStores);

			// 200 OK 상태 코드와 responseData를 JSON 형태로 반환
			return ResponseEntity.ok(responseData);
		}
	}
}