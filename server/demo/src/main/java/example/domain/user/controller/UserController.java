package example.domain.user.controller;

import example.domain.likes.entity.Likes;
import example.domain.likes.repository.LikesRepository;
import example.domain.likes.service.LikesService;
import example.domain.store.entity.Store;
import example.domain.store.repository.StoreRepository;
import example.domain.store.service.StoreService;
import example.domain.user.dto.SessionUser;
import example.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class UserController {
	private final SessionUser SessionUser;
	private final HttpSession httpSession;
	private final StoreRepository storeRepository;
	private final LikesService likesService;

	@GetMapping("/login")
		public String index(Model model) {
			SessionUser user = (SessionUser) httpSession.getAttribute("user");
			if( user != null ) {
				model.addAttribute("name", user.getName());
			}
			return "main";
		}

		@GetMapping("/mypage")
		public String myPage(Model model, HttpServletRequest request){
			HttpSession session = request.getSession();
			SessionUser sessionUser = (SessionUser) session.getAttribute("user");

			if(sessionUser == null) {
				return "redirect:/login";
			} else {
				System.out.println(sessionUser.getNickname());
				System.out.println(sessionUser.getEmail());

				model.addAttribute("user",sessionUser.getUser());
				model.addAttribute("nickname",sessionUser.getNickname());
				model.addAttribute("email",sessionUser.getEmail());

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
					model.addAttribute("user_id", likesStore.getUser().getId());
					model.addAttribute("store_id", likesStore.getStore().getStoreid());
				}
				model.addAttribute("likeStores",likeStores);
				return "mypage";
			}
		}
}
