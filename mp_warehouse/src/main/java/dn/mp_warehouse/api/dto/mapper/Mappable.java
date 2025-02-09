package dn.mp_warehouse.api.dto.mapper;

import java.util.List;
import java.util.Set;

public interface Mappable <E,D> {

    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);

    List<D> toDto(List<E> entityList);

    Set<E> toEntity(Set<D> dtoList);

    Set<D> toDto(Set<E> entityList);

//    @Mapping(target = "list", expression = "java(new PageImpl<>(entityList))")
//    ListDto<E> toListDto(List<E> entityLst);
}
