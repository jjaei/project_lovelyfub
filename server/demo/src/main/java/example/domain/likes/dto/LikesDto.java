package example.domain.likes.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikesDto {
    private Long userid;
    private Integer storeid;
}
