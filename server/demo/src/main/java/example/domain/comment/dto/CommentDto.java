package example.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {
    private String comment;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
