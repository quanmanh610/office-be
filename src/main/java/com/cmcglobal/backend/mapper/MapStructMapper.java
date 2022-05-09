package com.cmcglobal.backend.mapper;

import java.util.List;

public interface MapStructMapper<E, D> {
    E toEntity(D dto);

    D toDTO (E entity);

    List <E> toListEntity(List<D> dtoList);

    List <D> toListDTO(List<E> entityList);
}
