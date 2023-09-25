package example.domain.store.mapper;

import example.domain.store.dto.storeDetailsDto;
import example.domain.store.entity.Store;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-26T02:19:25+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.1.1.jar, environment: Java 11.0.17 (Azul Systems, Inc.)"
)
@Component
public class StoreMapperImpl implements StoreMapper {

    @Override
    public List<storeDetailsDto> storeToStoreResponseDto(List<Store> store) {
        if ( store == null ) {
            return null;
        }

        List<storeDetailsDto> list = new ArrayList<storeDetailsDto>( store.size() );
        for ( Store store1 : store ) {
            list.add( storeTostoreDetailsDto( store1 ) );
        }

        return list;
    }

    protected storeDetailsDto storeTostoreDetailsDto(Store store) {
        if ( store == null ) {
            return null;
        }

        storeDetailsDto storeDetailsDto = new storeDetailsDto();

        storeDetailsDto.setStoreid( store.getStoreid() );
        storeDetailsDto.setName( store.getName() );
        storeDetailsDto.setProfile( store.getProfile() );
        storeDetailsDto.setIntroduction( store.getIntroduction() );
        storeDetailsDto.setCategory( store.getCategory() );
        storeDetailsDto.setUsertype( store.getUsertype() );

        return storeDetailsDto;
    }
}
