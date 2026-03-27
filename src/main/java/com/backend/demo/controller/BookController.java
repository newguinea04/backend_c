package com.backend.demo.controller;

import com.backend.demo.dto.BookDto;
import com.backend.demo.model.Book;
import com.backend.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService booksService;

    @GetMapping
    public List<Book> getBooks() {
        return booksService.allBooks();
    }

    @PostMapping
    public Book createBook(@RequestBody BookDto bookDto) {
        // Service harus menangani setting Author berdasarkan authorId di DTO
        return booksService.createBook(bookDto);
    }

    @PutMapping("/{id}")
    public Book updateData(@PathVariable("id") Long id, @RequestBody BookDto bookDto){
        return booksService.updateBook(id, bookDto);
    }

    @DeleteMapping("/{id}")
    public void deleteDate(@PathVariable("id") Long id){
        booksService.delete(id);
    }
    
    // Contoh endpoint untuk Many-to-Many
    @PostMapping("/{bookId}/assign-to-user/{userId}")
    public void assignBookToUser(@PathVariable Long bookId, @PathVariable Long userId) {
        booksService.assignBookToUser(bookId, userId);
    }
}