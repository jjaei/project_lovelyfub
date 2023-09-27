package example.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {

    @Size(min = 1, max = 50, message = "1~50자 까지만 작성 가능해요")
    private String comment;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
