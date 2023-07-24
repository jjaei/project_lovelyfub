package example.domain.likes.repository;

import example.domain.likes.entity.Likes;
import example.domain.store.entity.Store;
import example.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByUserAndStore(User user, Store store);

}
