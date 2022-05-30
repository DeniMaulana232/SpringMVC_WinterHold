package com.indocyber.SpringMVC.repositories;

import com.indocyber.SpringMVC.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByLoans_Id(Long id);
}
