package example.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class ListResponseDto<T> {
    private List<T> data;
    public ListResponseDto(Iterable<T> iterable) {
        this.data = new ArrayList<>();
        iterable.forEach(this.data::add);
    }
}
