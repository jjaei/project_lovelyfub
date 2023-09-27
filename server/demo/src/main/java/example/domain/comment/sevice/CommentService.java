package example.domain.comment.sevice;

import example.domain.comment.dto.CommentDto;
import example.domain.comment.entity.Comment;
import example.domain.comment.repository.CommentRepository;
import example.domain.content.entity.Content;
import example.domain.content.repository.ContentRepository;
import example.domain.user.entity.User;
import example.domain.user.repository.UserRepository;
import example.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;

    // 로그인 검증
    private User getLoggedInUser(Principal principal) {
        if (principal == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
        String email = principal.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("로그인 한 유저 정보를 찾을 수 없습니다."));
    }

    // 댓글 작성
    @Transactional
    public Comment createComment(CommentDto commentDto, Principal principal, Integer contentId) {
        User loginUser = getLoggedInUser(principal);

        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));

        Comment comment = Comment.builder()
                .user(loginUser)
                .content(content)
                .comment(commentDto.getComment())
                .createDate(LocalDateTime.now())
                .build();
        return commentRepository.save(comment);
    }

    // 댓글 수정
    @Transactional
    public Comment updateComment(CommentDto commentDto, Principal principal, Long commentId) {
        User loginUser = getLoggedInUser(principal);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정할 댓글이 존재하지 않습니다."));

        User commentUser = comment.getUser();

        if (!loginUser.getEmail().equals(commentUser.getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "작성자만 게시글 수정이 가능합니다.");
        }
        comment.updateComment(commentDto, loginUser);
        return commentRepository.save(comment);
    }
}
