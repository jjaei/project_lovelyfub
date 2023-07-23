package example.domain.store.repository;

import example.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {
    Page<Store> findAllByType(String type, Pageable pageable);
    Optional<Store> findByStoreid(Integer store_id);
}