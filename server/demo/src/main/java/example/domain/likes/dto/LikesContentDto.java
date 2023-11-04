package example.domain.likes.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikesContentDto {
    private Long userid;
    private Integer contentid;
}
