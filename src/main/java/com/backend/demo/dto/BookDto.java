package com.backend.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class BookDto {
    private String title;
    private String isbn;
    private Long authorId;      // Untuk Many-to-One
    private List<Long> categoryIds; // Untuk Many-to-Many
}