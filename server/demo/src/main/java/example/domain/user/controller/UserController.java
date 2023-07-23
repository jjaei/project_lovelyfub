package example.domain.user.controller;

import example.domain.user.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

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
