package com.indocyber.SpringMVC.repositories;

import com.indocyber.SpringMVC.dtos.Loan.LoanDetailDTO;
import com.indocyber.SpringMVC.dtos.Loan.LoanGridDTO;
import com.indocyber.SpringMVC.models.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    @Query("""
            SELECT new com.indocyber.SpringMVC.dtos.Loan.LoanGridDTO
            (l.id, CONCAT(cus.firstName, ' ',cus.lastName), book.title, l.loanDate, l.dueDate, l.returnDate)
            FROM Loan l
            FULL JOIN l.customerNumber AS cus
            FULL JOIN l.bookCode as book
            WHERE CONCAT(cus.firstName, ' ',cus.lastName) LIKE %:name%
            AND book.title LIKE %:title%
            """)
    Page<LoanGridDTO> findAll(@Param("name") String name,
                              @Param("title") String title,
                              Pageable pageable);

    @Query("""
            SELECT new com.indocyber.SpringMVC.dtos.Loan.LoanDetailDTO
            (l.id,cus.id, book.title, cat.categoryName,
             CONCAT(aut.title, ' ',aut.firstName,' ',aut.lastName),
             cus.phone,
             CONCAT(cus.firstName,' ',cus.lastName),
             cus.membershipExpireDate,
             cat.floor, cat.isle, cat.bay)
            FROM Loan l
            FULL JOIN l.customerNumber as cus
            FULL JOIN l.bookCode as book
            FULL JOIN book.author as aut
            FULL JOIN book.categoryName as cat
            WHERE l.id = :id
            """)
    LoanDetailDTO findLoanById(Long id);
}
