package example.domain.user.service;

import example.domain.content.entity.Content;
import example.domain.content.repository.ContentRepository;
import example.domain.likes.entity.Likes;
import example.domain.likes.service.LikesService;
import example.domain.store.entity.Store;
import example.domain.user.dto.UserProfileResponse;
import example.domain.user.entity.User;
import example.domain.user.repository.UserRepository;
import example.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LikesService likesService;
    private final ContentRepository contentRepository;

    // 로그인 검증
    private User getLoggedInUser(Principal principal) {
        if (principal == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
        String email = principal.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("로그인 한 유저 정보를 찾을 수 없습니다."));
    }

    // 유저 정보 가져오기
    public User oauthLoginInfo(Principal principal) {
        User loginUser = getLoggedInUser(principal);
        return getLoggedInUser(principal);
    }

    // 좋아요한 가게 목록 가져오기
    public List<Store> getMyStores(Principal principal) {
        User loginUser = getLoggedInUser(principal);

        List<Likes> likeStores = likesService.getAllLikes();

        List<Store> myLikeStores = likeStores.stream()
                .filter(likeStore -> loginUser.equals(likeStore.getUser()))
                .map(Likes::getStore)
                .collect(Collectors.toList());

        return myLikeStores;
    }

    // 내가 작성한 컨텐츠 목록 가져오기
    public List<Content> getMyContents(Principal principal) {
        User loginUser = getLoggedInUser(principal);

        return contentRepository.findByUserId(loginUser.getId());
    }

    // 유저 정보 수정
    @Transactional
    public User updateUserInfo(Principal principal, User updateUser) {
        User loginUser = getLoggedInUser(principal);
        loginUser.setSns_link(updateUser.getSns_link());
        userRepository.save(loginUser);
        return loginUser;
    }

    // 유저 프로필 조회하기
    public UserProfileResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저가 존재하지 않습니다."));
        List<Content> userContents = contentRepository.findByUserId(userId);
        UserProfileResponse response = new UserProfileResponse();
        response.setNickname(user.getNickname());
        response.setSns_link(user.getSns_link());
        response.setPicture(user.getPicture());
        response.setContents(userContents);
        return response;
    }
}
