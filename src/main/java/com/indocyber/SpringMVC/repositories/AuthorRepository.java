package com.indocyber.SpringMVC.repositories;

import com.indocyber.SpringMVC.dtos.Author.AuthorBookGridDTO;
import com.indocyber.SpringMVC.dtos.Author.AuthorDTO;
import com.indocyber.SpringMVC.models.Author;
import com.indocyber.SpringMVC.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author,Long> {
    @Query("""
            SELECT new com.indocyber.SpringMVC.dtos.Author.AuthorDTO
            (a.id, a.title, CONCAT(a.title, ' ',a.firstName , ' ', a.lastName),
            a.birthDate, a.deceasedDate,
            a.education, a.summary)
            FROM Author a
            WHERE CONCAT(a.firstName, ' ', a.lastName) LIKE %:fullName%
            """)
    Page<AuthorDTO> findAll(@Param("fullName") String fullName, Pageable pageable);

    @Query("""
            SELECT new com.indocyber.SpringMVC.dtos.Author.AuthorBookGridDTO
            (b.id, b.title, cat.categoryName, b.isBorrowed, b.releaseDate, b.totalPage)
            FROM Book b
            INNER JOIN b.categoryName AS cat
            LEFT JOIN b.author AS aut
            where aut.id = :id
           """)
    List<AuthorBookGridDTO> findAllAuthorBookGridDTO(Long id);
}
//    select b.Code, b.Title, a.id, c.Name, b.IsBorrowed, b.ReleaseDate, b.TotalPage from Book as b
//        join Author as a on b.AuthorId = a.id
//        join Category as c on c.[Name] = b.CategoryName
//        where a.Id = :id
