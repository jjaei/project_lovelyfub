package example.domain.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
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
    @Column(nullable = false)
    private Float latitude;
    @Column(nullable = false)
    private Float longitude;
}