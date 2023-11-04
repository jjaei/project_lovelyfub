package example.domain.content.repository;

import example.domain.content.entity.Content;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentLikeRepository {
    void addLikeCount(Content content);
    void deleteLikeCount(Content content);


}
