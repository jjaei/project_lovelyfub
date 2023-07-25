package example.domain.store.repository;

import example.domain.store.entity.Store;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreLikeRepository {
    void addLikeCount(Store store);
    void deleteLikeCount(Store store);


}
