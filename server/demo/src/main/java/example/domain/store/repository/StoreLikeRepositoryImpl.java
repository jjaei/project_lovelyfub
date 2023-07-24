package example.domain.store.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import example.domain.store.entity.QStore;
import example.domain.store.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Repository
public class StoreLikeRepositoryImpl implements StoreLikeRepository {

    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    @Override
    public void addLikeCount(Store seletedStore) {
        QStore qStore = QStore.store;
        queryFactory.update(qStore)
                .set(qStore.likeCount, qStore.likeCount.add(1))
                .where(qStore.eq(seletedStore))
                .execute();
    }
    @Override
    public void deleteLikeCount(Store selectedStore){
        QStore qStore = QStore.store;
        queryFactory.update(qStore)
                .set(qStore.likeCount, qStore.likeCount.subtract(1))
                .where(qStore.eq(selectedStore))
                .execute();
    }
}