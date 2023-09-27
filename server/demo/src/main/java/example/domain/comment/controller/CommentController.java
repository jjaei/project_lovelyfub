package example.domain.comment.controller;

import example.domain.comment.dto.CommentDto;
import example.domain.comment.entity.Comment;
import example.domain.comment.sevice.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/{contentId}")
    public Comment createComments(@RequestBody CommentDto commentDto, Principal principal, @PathVariable Integer contentId) {
        return commentService.createComment(commentDto, principal, contentId);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public Comment updateComments(@RequestBody CommentDto commentDto, Principal principal, @PathVariable Long commentId) {
        return commentService.updateComment(commentDto, principal, commentId);
    }
}
