package dn.mp_warehouse.api.dto.mapper;

import dn.mp_warehouse.api.dto.shop.ShopOutDto;
import dn.mp_warehouse.domain.entity.ShopEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShopMapper extends Mappable<ShopEntity, ShopOutDto>{
}
