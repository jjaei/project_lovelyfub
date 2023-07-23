package example.domain.store.mapper;

import example.domain.store.dto.storeDetailsDto;
import example.domain.store.dto.storeResponseDto;
import example.domain.store.entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.modelmapper.ModelMapper;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface StoreMapper {
        List<storeDetailsDto> storeToStoreResponseDto(List<Store> store);

        default storeResponseDto detailToDetailResponseDto(Store store) {
                ModelMapper modelMapper = new ModelMapper();
                storeResponseDto storeResponseDto = modelMapper.map(store, storeResponseDto.class);
                storeResponseDto.setStoreid(store.getStoreid());
                return storeResponseDto;
        }
}