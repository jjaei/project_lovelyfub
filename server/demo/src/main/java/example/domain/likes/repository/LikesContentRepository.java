package example.domain.likes.repository;

import example.domain.content.entity.Content;
import example.domain.likes.entity.LikesContent;
import example.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesContentRepository extends JpaRepository<LikesContent, Long> {
    Optional<LikesContent> findByUserAndContent(User user, Content content);
}
