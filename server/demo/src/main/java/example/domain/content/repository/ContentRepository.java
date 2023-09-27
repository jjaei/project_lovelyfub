package example.domain.content.repository;

import example.domain.content.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Integer> {
    List<Content> findByUserId(Long userId);
}
