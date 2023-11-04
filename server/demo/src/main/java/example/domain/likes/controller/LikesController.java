package example.domain.likes.controller;

import example.domain.likes.dto.LikesContentDto;
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

    @PostMapping("/store")
    public ResponseEntity<?> insertLke(@RequestBody @Valid LikesDto likesDto) throws Exception {
        likesService.insertLike(likesDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/content")
    public ResponseEntity<?> insertLkeContent(@RequestBody @Valid LikesContentDto likesContentDto) throws Exception {
        likesService.insertLikeContent(likesContentDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value="/store/{userid}/{storeid}", produces="text/plain; charset=utf-8")
    public ResponseEntity<?> deleteLike(
            @PathVariable("userid") Long userid,
            @PathVariable("storeid") Integer storeid,
            LikesDto likesDto) {
        likesService.deleteLike(likesDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value="/content/{userid}/{contentid}", produces="text/plain; charset=utf-8")
    public ResponseEntity<?> deleteLikeContent(
            @PathVariable("userid") Long userid,
            @PathVariable("contentid") Integer contentid,
            LikesContentDto likesContentDto) {
        likesService.deleteLikeContent(likesContentDto);
        return ResponseEntity.ok().build();
    }
}
