package example.domain.comment.controller;

import example.domain.comment.dto.CommentDto;
import example.domain.comment.entity.Comment;
import example.domain.comment.sevice.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/{contentId}")
    public Comment createComment(@Valid @RequestBody CommentDto commentDto, Principal principal, @PathVariable Integer contentId) {
        return commentService.createComment(commentDto, principal, contentId);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public Comment updateComment(@Valid @RequestBody CommentDto commentDto, Principal principal, @PathVariable Long commentId) {
        return commentService.updateComment(commentDto, principal, commentId);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public Comment deleteComment(@PathVariable Long commentId, Principal principal) {
        return commentService.deleteComment(principal, commentId);
    }

    // 댓글 조회
    @GetMapping("/{contentId}")
    public List<Comment> getComments(@PathVariable Integer contentId, Principal principal,
                                     @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Comment> pageComments = commentService.getComments(principal, contentId, pageable);
        return pageComments.getContent();
    }
}
