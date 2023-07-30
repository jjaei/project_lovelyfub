package example.domain.user.controller;

import example.domain.likes.entity.Likes;
import example.domain.likes.service.LikesService;
import example.domain.store.entity.Store;
import example.domain.store.repository.StoreRepository;
import example.domain.user.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RequiredArgsConstructor
@RestController
public class UserController {
	private final HttpSession httpSession;
	private final StoreRepository storeRepository;
	private final LikesService likesService;

	@GetMapping("/user")
	public ResponseEntity<Object> login(Model model) {
		SessionUser user = (SessionUser) httpSession.getAttribute("user");
		if (user != null) {
			Map<String, Object> userInfo = new HashMap<>();
			userInfo.put("name", user.getName());
			userInfo.put("id", user.getId());
			userInfo.put("email", user.getEmail());
			userInfo.put("nickname", user.getNickname());
			return ResponseEntity.ok(userInfo);
		} else {
			return ResponseEntity.ok(Collections.emptyMap());
		}
	}

	@GetMapping("/mypage")
	public ResponseEntity<Map<String, Object>> myPage(Model model, HttpServletRequest request){
		HttpSession session = request.getSession();
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");

		if(sessionUser == null) {
			// 로그인이 되어있지 않으면 401 Unauthorized 응답을 반환하도록 설정
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} else {
			System.out.println(sessionUser.getNickname());
			System.out.println(sessionUser.getEmail());
			Map<String, Object> responseData = new HashMap<>();

			responseData.put("name",sessionUser.getName());
			responseData.put("nickname",sessionUser.getNickname());
			responseData.put("email",sessionUser.getEmail());

			List<Likes> likeStore = likesService.getAllLikes();
			List<Store> likeStores = new ArrayList<>();

			for(Likes likesStore : likeStore){
				if(sessionUser.getId().equals(likesStore.getUser().getId())){
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
