package example.domain.store.controller;

import example.domain.store.entity.Store;
import example.domain.store.mapper.StoreMapper;
import example.domain.store.service.StoreService;
import example.global.exception.BusinessLogicException;
import example.global.exception.ExceptionCode;
import example.global.response.MultiResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Positive;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping
@Validated
@Slf4j
public class StoreController {

    private StoreService service;
    private StoreMapper mapper;

    public StoreController(StoreService service, StoreMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }
    //식당 리스트
    @GetMapping("/cafe")
    public ResponseEntity getCafeList(@Positive @RequestParam int page, @Positive @RequestParam int size) {
        Page<Store> pageStore = service.findStore("cafe",page -1, size);
        List<Store> store = pageStore.getContent();
        for (Store st : store) {
            String imageUrl = st.getProfile();
            try {
                byte[] fileContent = FileUtils.readFileToByteArray(new File(imageUrl));
                String encodedString = Base64.getEncoder().encodeToString(fileContent);
                st.setProfile(encodedString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(new MultiResponseDto<>(mapper.storeToStoreResponseDto(store), pageStore), HttpStatus.OK);
    }
    //마켓리스트
    @GetMapping("/market")
    public ResponseEntity getMarketList(@Positive @RequestParam int page, @Positive @RequestParam int size) {
        Page<Store> pageStore = service.findStore("market",page -1, size);
        System.out.println(pageStore);
        List<Store> store = pageStore.getContent();
        for (Store st : store) {
            String imageUrl = st.getProfile();
            try {
                byte[] fileContent = FileUtils.readFileToByteArray(new File(imageUrl));
                String encodedString = Base64.getEncoder().encodeToString(fileContent);
                st.setProfile(encodedString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(new MultiResponseDto<>(mapper.storeToStoreResponseDto(store), pageStore), HttpStatus.OK);
    }
    //상세페이지
    @GetMapping("/store/{id}")
    public ResponseEntity getCafe(@PathVariable("id") @Positive int storeid) {
        Store store = service.findStoreDetail(storeid);
        String imageUrl = store.getProfile();
        try {
            byte[] fileContent = FileUtils.readFileToByteArray(new File(imageUrl));
            String encodedString = Base64.getEncoder().encodeToString(fileContent);
            store.setProfile(encodedString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(mapper.detailToDetailResponseDto(store), HttpStatus.OK);
    }
    //주변가게찾기
    @GetMapping("/map")
    public ResponseEntity getNearStoreList(@RequestParam float latitude, @RequestParam float longitude) {
        List<Store> store = service.getStoresWithinRadius(latitude,longitude,500);
        return new ResponseEntity<>(mapper.storeToStoreResponseDto(store), HttpStatus.OK);
    }
}