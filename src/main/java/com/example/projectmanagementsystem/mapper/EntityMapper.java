package com.example.projectmanagementsystem.mapper;

import java.util.Set;

public interface EntityMapper<D, E> {

    E toEntity(D dto);

    D toDto(E entity);

    Set<E> toEntity(Set<D> dtoList);

    Set<D> toDto(Set<E> entityList);
}
