package example.domain.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.lovelyfub.domain.user.dto.SessionUser;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
	private final HttpSession httpSession;
	
	@GetMapping("/main")
	public String index(Model model) {
		SessionUser user = (SessionUser) httpSession.getAttribute("user");
		if( user != null ) {
			model.addAttribute("name", user.getName());
		}
		
		return "main";
	}
	
}
