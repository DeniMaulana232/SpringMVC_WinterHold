package com.indocyber.SpringMVC.repositories;

import com.indocyber.SpringMVC.dtos.Author.AuthorBookGridDTO;
import com.indocyber.SpringMVC.dtos.Author.AuthorDTO;
import com.indocyber.SpringMVC.dtos.Books.BookDto;
import com.indocyber.SpringMVC.dtos.Category.CategoriGridDTO;
import com.indocyber.SpringMVC.dtos.Category.CategoryBookGridDTO;
import com.indocyber.SpringMVC.dtos.Customer.CustomerGridDto;
import com.indocyber.SpringMVC.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {
//    @Query(value = """
//            select cat.Name, cat.Floor ,cat.Isle,cat.Bay, count(b.CategoryName) as books
//            from Category cat JOIN Book as b on cat.Name = b.CategoryName
//            WHERE cat.Name LIKE %:Name%
//            group by cat.Name, cat.Floor ,cat.Isle,cat.Bay
//            """, nativeQuery = true)
//    Page<Category> findAll(@Param("Name") String categoryName, Pageable pageable);

    Page<Category> findByCategoryNameContaining(String categoryName, Pageable pageable);

    @Query("""
            SELECT new com.indocyber.SpringMVC.dtos.Category.CategoryBookGridDTO
            (CONCAT(aut.title,' ',aut.firstName,' ',aut.lastName), b.title , cat.categoryName, b.isBorrowed, b.releaseDate, b.totalPage)
            FROM Book b
            INNER JOIN b.categoryName AS cat
            LEFT JOIN b.author AS aut
            where cat.categoryName = :categoryName
            AND b.title LIKE %:title%
            AND CONCAT(aut.title,' ',aut.firstName,' ',aut.lastName) LIKE %:id%
           """)
    Page<CategoryBookGridDTO> findAllAuthorBookGridDTO(String categoryName,
                                                       @Param("title") String title,
                                                       @Param("id") String id,
                                                       Pageable pageable);

    @Query("""
            SELECT new com.indocyber.SpringMVC.dtos.Books.BookDto
            (b.id, b.title, b.isBorrowed, b.summary, b.releaseDate, b.totalPage)
            FROM Book b
            WHERE b.isBorrowed = '0'
            """)
    List<BookDto> findAllBookAvailable();

}
