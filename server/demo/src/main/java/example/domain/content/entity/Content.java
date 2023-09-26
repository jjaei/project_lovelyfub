package example.domain.content.entity;

import example.domain.store.entity.Store;
import example.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "content")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contentId;

    private Integer storeId;

    private Long userId;
    @ElementCollection
    @CollectionTable(name = "content_image_urls", joinColumns = @JoinColumn(name = "content_id"))
    @Column(name = "ImageUrls")
    private List<String> imageUrls;

    private Integer rating;

    private String contentText;

}