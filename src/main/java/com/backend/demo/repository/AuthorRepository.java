package com.backend.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.demo.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    // Kamu bisa menambah custom query di sini nanti, 
    // contoh: findByName(String name);
}