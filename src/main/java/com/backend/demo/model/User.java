package com.backend.demo.model;

import jakarta.persistence.*;
import lombok.*; // Gunakan bintang agar semua library lombok terbaca

@Entity
@Table(name = "users")
@Data // Ini yang paling penting agar getEmail() dkk muncul
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String fullName;
}