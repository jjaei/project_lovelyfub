package example.domain.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "store")
public class Store {
    @Id
    private Integer storeid;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String profile;
    @Column(nullable = false)
    private String introduction;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String number;
    @Column
    private String instagram;

    @ColumnDefault("0")
    @Column(name = "likecount", nullable = false)
    private Integer likeCount;
    @Column(nullable = false)
    private Float latitude;
    @Column(nullable = false)
    private Float longitude;
}