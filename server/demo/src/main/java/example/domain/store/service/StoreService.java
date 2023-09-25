package example.domain.store.service;

import example.domain.store.entity.Store;
import example.domain.store.repository.StoreRepository;
import example.global.exception.BusinessLogicException;
import example.global.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreService {

    private StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Transactional(readOnly = true)
    public Page<Store> findStore(String location, String detaillocation, String type, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        if(location!=null&&detaillocation!=null) {
            return storeRepository.findAllByLocationAndDetaillocationAndType(location, detaillocation, type, pageable);
        }
        else if(location==null&&detaillocation!=null){
            return storeRepository.findAllByDetaillocationAndType(detaillocation, type, pageable);
        }
        else if(location!=null&&detaillocation==null){
            return storeRepository.findAllByLocationAndType(location,type,pageable);
        }
        else{
            return storeRepository.findAllByType(type, pageable);
        }
    }

    @Transactional(readOnly = true)
    public Page<Store> findStore(String location, String detaillocation, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        if(location!=null&&detaillocation!=null) {
            return storeRepository.findAllByLocationAndDetaillocation(location, detaillocation,  pageable);
        }
        else if(location==null&&detaillocation!=null){
            return storeRepository.findAllByDetaillocation(detaillocation,  pageable);
        }
        else if(location!=null&&detaillocation==null){
            return storeRepository.findAllByLocation(location,pageable);
        }
        else{
            return storeRepository.findAll(pageable);
        }
    }
    public List<Store> findStoreKeyword(String keyword) {
        return storeRepository.findByNameContaining(keyword);
    }
    public Store findStoreDetail(int storeid) {
        return storeRepository.findByStoreid(storeid)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.STORE_NOT_FOUND));
    }

    public List<Store> getStoresWithinRadius(double latitude, double longitude, double radius) {
        List<Store> storesWithinRadius = new ArrayList<>();

        for (Store store : storeRepository.findAll()) {
            if (calculateDistance(latitude, longitude, store.getLatitude(), store.getLongitude()) <= radius) {
                storesWithinRadius.add(store);
            }
        }

        return storesWithinRadius;
    }
    private static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371.0;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c * 1000;
    }
}
