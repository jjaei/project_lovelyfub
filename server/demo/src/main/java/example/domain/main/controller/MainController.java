package example.domain.main.controller;

import example.domain.user.entity.User;
import example.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
@Validated
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final UserRepository userRepository;

    @GetMapping("/")
    public ResponseEntity getMainPage() {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/main")
    public List<User> getTop10LovelyUsers() {
        List<User> top10Users = userRepository.findTop10ByOrderByLovelyCountDesc();
        return top10Users;
    }




}
