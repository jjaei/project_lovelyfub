package example.domain.main.controller;

import example.domain.store.entity.Store;
import example.global.response.MultiResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping
@Validated
@Slf4j
public class MainController {

        @GetMapping("/")
        public ResponseEntity getMainPage() {

            return new ResponseEntity<>(HttpStatus.OK);
        }

}
