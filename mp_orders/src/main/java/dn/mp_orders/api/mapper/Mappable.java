package dn.mp_orders.api.mapper;



import java.util.List;
import java.util.Set;


public interface Mappable <E,D>{

    E fromDto(D dto);

    D toDto(E dto);

    List<D> toDto(List<E> dto);

    List<E> fromDto(List<D> dto);

    Set<D> toDto(Set<E> dto);

    Set<E> fromDto(Set<D> dto);

}
