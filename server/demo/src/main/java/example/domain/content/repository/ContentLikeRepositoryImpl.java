package example.domain.content.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import example.domain.content.entity.Content;
import example.domain.content.entity.QContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Repository
public class ContentLikeRepositoryImpl implements ContentLikeRepository {

    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    @Override
    public void addLikeCount(Content seletedContent) {
        QContent qContent = QContent.content;
        queryFactory.update(qContent)
                .set(qContent.likeCount, qContent.likeCount.add(1))
                .where(qContent.eq(seletedContent))
                .execute();
    }
    @Override
    public void deleteLikeCount(Content selectedContent){
        QContent qContent = QContent.content;
        queryFactory.update(qContent)
                .set(qContent.likeCount, qContent.likeCount.subtract(1))
                .where(qContent.eq(selectedContent))
                .execute();
    }
}