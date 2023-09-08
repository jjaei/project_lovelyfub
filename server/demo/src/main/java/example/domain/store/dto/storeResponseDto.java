package example.domain.store.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class storeResponseDto {
    private Integer storeid;
    private String type;
    private String name;
    private String profile;
    private String introduction;
    private String address;
    private String number;
    private String instagram;
    private String location;
    private String detaillocation;
    private float latitude;
    private float longitude;
}
