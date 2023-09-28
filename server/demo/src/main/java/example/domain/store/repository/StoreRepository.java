package example.domain.store.repository;

import example.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {
    Page<Store> findAllByType(String type, Pageable pageable);
    Page<Store> findAllByLocationAndType(String location, String type, Pageable pageable);
    Page<Store> findAllByDetaillocationAndType(String detaillocation, String type, Pageable pageable);
    Page<Store> findAllByLocationAndDetaillocationAndType(String location, String detaillocation, String type, Pageable pageable);
    Optional<Store> findByStoreid(Integer store_id);

    List<Store> findByNameContaining(String keyword);

    Page<Store> findAll(Pageable pageable);

    Page<Store> findAllByDetaillocation(String detaillocation, Pageable pageable);



    //1개
    //카테고리
    //유저타입
    //로케이션
    Page<Store> findAllByCategory(String category, Pageable pageable);
    Page<Store> findAllByUsertype(String usertype, Pageable pageable);
    Page<Store> findAllByLocation(String location, Pageable pageable);

    //2개
    //카테고리,유저타입
    //카테고리,로케이션
    //유저타입,로케이션
    //로케이션,디테일로케이션
    Page<Store> findAllByCategoryAndUsertype(String category, String usertype, Pageable pageable);
    Page<Store> findAllByCategoryAndLocation(String category, String location, Pageable pageable);
    Page<Store> findAllByUsertypeAndLocation(String usertype, String location, Pageable pageable);
    Page<Store> findAllByLocationAndDetaillocation(String location, String detaillocation, Pageable pageable);


    //3개
    //카테고리,유저타입,로케이션
    //카테고리,로케이션,디테일로케이션
    //유저타입,로케이션,디테일로케이션
    Page<Store> findAllByCategoryAndUsertypeAndLocation(String category, String usertype, String location, Pageable pageable);
    Page<Store> findAllByCategoryAndLocationAndDetaillocation(String category, String location, String detaillocation, Pageable pageable);
    Page<Store> findAllByUsertypeAndLocationAndDetaillocation(String usertype, String location, String detaillocation, Pageable pageable);

    //4개
    //카테고리,유저타입,로케이션,디테일로케이션
    Page<Store> findAllByCategoryAndUsertypeAndLocationAndDetaillocation(String category, String usertype, String location, String detaillocation, Pageable pageable);

}