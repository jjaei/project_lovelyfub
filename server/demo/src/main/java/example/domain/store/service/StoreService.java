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
@Service
public class StoreService {

    private StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Transactional(readOnly = true)
    public Page<Store> findStore(String type, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return storeRepository.findAllByType(type, pageable);
    }

    public Store findStoreDetail(int storeid) {
        return storeRepository.findByStoreid(storeid)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.STORE_NOT_FOUND));
    }
}
