package example.domain.comment.entity;

import example.domain.comment.dto.CommentDto;
import example.domain.content.entity.Content;
import example.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contentId")
    private Content content;

    private String comment;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    // 댓글 수정 로직 추가
    public Comment updateComment(CommentDto commentDto, User user) {
        this.comment = commentDto.getComment();
        this.updateDate = LocalDateTime.now();
        this.user = user;
        return this;
    }
}
