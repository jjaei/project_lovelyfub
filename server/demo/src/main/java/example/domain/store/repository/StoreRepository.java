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
    Page<Store> findAllByLocationAndType(String location, String type, Pageable pageable);
    Page<Store> findAllByDetaillocationAndType(String detaillocation, String type, Pageable pageable);
    Page<Store> findAllByLocationAndDetaillocationAndType(String location, String detaillocation, String type, Pageable pageable);
    Optional<Store> findByStoreid(Integer store_id);

    Optional<Store> findByNameContaining(String keyword);

    Page<Store> findAll(Pageable pageable);
    Page<Store> findAllByLocation(String location, Pageable pageable);
    Page<Store> findAllByDetaillocation(String detaillocation, Pageable pageable);
    Page<Store> findAllByLocationAndDetaillocation(String location, String detaillocation, Pageable pageable);

}