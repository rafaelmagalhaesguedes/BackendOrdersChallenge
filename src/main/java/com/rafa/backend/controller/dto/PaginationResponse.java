package com.rafa.backend.controller.dto;

import org.springframework.data.domain.Page;

public record PaginationResponse(
        Integer page,
        Integer pageSize,
        Long totalElements,
        Integer totalPages
) {
    public static <T> PaginationResponse fromPage(Page<T> page) {
        return new PaginationResponse(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
