package example.domain.user.controller;

import example.domain.content.entity.Content;
import example.domain.store.entity.Store;
import example.domain.user.entity.User;
import example.domain.user.service.UserService;
import example.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mypage")
public class MypageController {

    private final UserService userService;

    @GetMapping("/profile")
    public User myProfile(Principal principal) {
        User user = userService.oauthLoginInfo(principal);
        if (user != null) {
            return user;
        } else {
            throw new NotFoundException("로그인 유저를 찾을 수 없습니다.");
        }
    }

    // 좋아요 한 가게목록
    @GetMapping("/likeStores")
    public List<Store> myStroes(Principal principal) {
        User user = userService.oauthLoginInfo(principal);
        if (user != null) {
            return userService.getMyStores(principal);
        } else {
            throw new NotFoundException("로그인 유저를 찾을 수 없습니다.");
        }
    }

    @GetMapping("/contents")
    public List<Content> myContents(Principal principal) {
        User user = userService.oauthLoginInfo(principal);
        if (user != null) {
            return userService.getMyContents(principal);
        } else {
            throw new NotFoundException("로그인 유저를 찾을 수 없습니다.");
        }
    }

    // 좋아요 한 콘텐츠 목록
    @GetMapping("/likeContents")
    public List<Content> likeContent(Principal principal) {
        User user = userService.oauthLoginInfo(principal);
        if (user != null) {
            return userService.getLikeContent(principal);
        } else {
            throw new NotFoundException("로그인 유저를 찾을 수 없습니다.");
        }
    }

}
