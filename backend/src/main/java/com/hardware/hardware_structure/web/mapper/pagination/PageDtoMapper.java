package com.hardware.hardware_structure.web.mapper.pagination;

import com.hardware.hardware_structure.web.dto.pagination.PageDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageDtoMapper {
    public static <D, E> PageDto<D> toDto(Page<E> page, Function<E, D> mapper) {
        final PageDto<D> dto = new PageDto<>();
        dto.setItems(page.getContent().stream().map(mapper).toList());
        dto.setItemsCount(page.getNumberOfElements());
        dto.setCurrentPage(page.getNumber());
        dto.setCurrentSize(page.getSize());
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalItems(page.getTotalElements());
        dto.setFirst(page.isFirst());
        dto.setLast(page.isLast());
        dto.setHasNext(page.hasNext());
        dto.setHasPrevious(page.hasPrevious());
        return dto;
    }
}
