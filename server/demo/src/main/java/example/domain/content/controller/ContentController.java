package example.domain.content.controller;

import example.domain.content.service.ContentService;
import example.domain.user.entity.User;
import example.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Validated
@Slf4j
public class ContentController {

    private final UserRepository userRepository;
    private final ContentService contentService;

    @PostMapping("/content")
    public void postContent(Integer storeid, @RequestParam("files") List<MultipartFile> files, Integer rating, String contenttext, Principal principal) throws IOException {
        if(principal!=null) {
            String email = principal.getName();
            Optional<User> optionalUser = userRepository.findByEmail(email);
            contentService.createContent(storeid, optionalUser.get().getId(), files, rating, contenttext);
        }
        else{
            contentService.createContent(storeid, 1L, files, rating, contenttext);
        }
    }
}
