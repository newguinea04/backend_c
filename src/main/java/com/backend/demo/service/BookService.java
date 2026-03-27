package com.backend.demo.service;

import java.util.List;

import com.backend.demo.dto.BookDto;
import com.backend.demo.model.Book;

public interface BookService {
    List<Book> allBooks();
    Book createBook(BookDto bookDto);
    Book updateBook(Long id, BookDto bookDto);
    void delete(Long id);
    // Tambahkan baris ini agar Controller tidak error:
    void assignBookToUser(Long bookId, Long userId); 
}