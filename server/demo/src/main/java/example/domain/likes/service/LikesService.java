package example.domain.likes.service;

import example.domain.likes.dto.LikesDto;
import example.domain.likes.entity.Likes;
import example.domain.likes.repository.LikesRepository;
import example.domain.store.entity.Store;
import example.domain.store.repository.StoreLikeRepository;
import example.domain.store.repository.StoreRepository;
import example.domain.user.entity.User;
import example.domain.user.repository.UserRepository;
import example.global.exception.DuplicateResourceException;
import example.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final StoreLikeRepository storeLikeRepository;

    @Transactional
    public void insertLike(LikesDto likesDto) throws Exception {
        User user = userRepository.findById(likesDto.getUserid())
                .orElseThrow(() -> new NotFoundException("Could not found User Id : " + likesDto.getUserid()));

        Store store = storeRepository.findById(likesDto.getStoreid())
                .orElseThrow(() -> new NotFoundException("Could not found Store Id : " + likesDto.getStoreid()));

        // 이미 좋아요가 눌린 상태라면 에러 반환
        if (likesRepository.findByUserAndStore(user, store).isPresent()) {
            throw new DuplicateResourceException("already exist data by user id : " + user.getId() + " ,"
                    + "store id : " + store.getStoreid());
        }

        Likes likes = Likes.builder()
                .store(store)
                .user(user)
                .build();

        likesRepository.save(likes);
        storeLikeRepository.addLikeCount(store);
    }

    @Transactional
    public void deleteLike(LikesDto likesDto) {
        User user = userRepository.findById(likesDto.getUserid())
                .orElseThrow(() -> new NotFoundException("Could not found User Id : " + likesDto.getUserid()));

        Store store = storeRepository.findById(likesDto.getStoreid())
                .orElseThrow(() -> new NotFoundException("Could not found Store Id : " + likesDto.getStoreid()));

        Likes likes = likesRepository.findByUserAndStore(user, store)
                .orElseThrow(() -> new NotFoundException("Could not found like id"));

        likesRepository.delete(likes);
        storeLikeRepository.deleteLikeCount(store);
    }

    public List<Likes> getAllLikes() {
        return likesRepository.findAll();
    }

}