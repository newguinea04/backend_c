package com.backend.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.demo.dto.BookDto;
import com.backend.demo.model.Author;
import com.backend.demo.model.Book;
import com.backend.demo.model.Category;
import com.backend.demo.model.User;
import com.backend.demo.repository.AuthorRepository;
import com.backend.demo.repository.BookRepository;
import com.backend.demo.repository.CategoryRepository;
import com.backend.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository; // Ditambahkan untuk assignBookToUser

    @Override
    public List<Book> allBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public Book createBook(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setIsbn(bookDto.getIsbn()); // Error 'cannot find symbol' hilang jika model Book sudah diupdate

        // Handling Many-to-One: Author
        if (bookDto.getAuthorId() != null) {
            Author author = authorRepository.findById(bookDto.getAuthorId())
                    .orElseThrow(() -> new RuntimeException("Author not found"));
            book.setAuthor(author);
        }

        // Handling Many-to-Many: Categories
        if (bookDto.getCategoryIds() != null && !bookDto.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(bookDto.getCategoryIds());
            book.setCategories(categories);
        }

        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book updateBook(Long id, BookDto bookDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setTitle(bookDto.getTitle());
        book.setIsbn(bookDto.getIsbn());

        if (bookDto.getAuthorId() != null) {
            Author author = authorRepository.findById(bookDto.getAuthorId())
                    .orElseThrow(() -> new RuntimeException("Author not found"));
            book.setAuthor(author);
        }

        if (bookDto.getCategoryIds() != null) {
            List<Category> categories = categoryRepository.findAllById(bookDto.getCategoryIds());
            book.setCategories(categories);
        }

        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    // Solusi untuk ERROR di BookController baris 42
    @Override
    @Transactional
    public void assignBookToUser(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Contoh Logika: Menambahkan buku ke daftar koleksi user
        // Pastikan di model User ada field: List<Book> books;
        user.getBorrowedBooks().add(book);
        userRepository.save(user);
    }
}