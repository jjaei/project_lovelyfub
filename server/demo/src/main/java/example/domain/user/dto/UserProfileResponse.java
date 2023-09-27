package example.domain.user.dto;

import example.domain.content.entity.Content;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserProfileResponse {
    private String nickname;
    private String picture;
    private String sns_link;
    private List<Content> contents;
}
