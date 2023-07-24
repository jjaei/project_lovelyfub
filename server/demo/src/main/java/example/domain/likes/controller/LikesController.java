package example.domain.likes.controller;

import example.domain.likes.dto.LikesDto;
import example.domain.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikesController {

    private final LikesService likesService;

    @PostMapping
    public ResponseEntity<?> insertLke(@RequestBody @Valid LikesDto likesDto) throws Exception {
        likesService.insertLike(likesDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value="{userid}/{storeid}", produces="text/plain; charset=utf-8")
    public ResponseEntity<?> deleteLike(
            @PathVariable("userid") Long userid,
            @PathVariable("storeid") Integer storeid,
            LikesDto likesDto) {
        likesService.deleteLike(likesDto);
        return ResponseEntity.ok().build();
    }
}
