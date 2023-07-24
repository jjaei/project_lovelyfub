package example.domain.likes.entity;

import example.domain.store.entity.Store;
import example.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeid")
    private Store store;

    @Builder
    public Likes(User user, Store store) {
        this.user = user;
        this.store = store;
    }
}
