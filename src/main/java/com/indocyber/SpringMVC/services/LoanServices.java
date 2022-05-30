package com.indocyber.SpringMVC.services;

import com.indocyber.SpringMVC.dtos.Author.AuthorDTO;
import com.indocyber.SpringMVC.dtos.Loan.LoanDetailDTO;
import com.indocyber.SpringMVC.dtos.Loan.LoanGridDTO;
import com.indocyber.SpringMVC.dtos.Loan.UpsertLoanDTO;
import com.indocyber.SpringMVC.models.Book;
import com.indocyber.SpringMVC.models.Customer;
import com.indocyber.SpringMVC.models.Loan;
import com.indocyber.SpringMVC.repositories.BookRepository;
import com.indocyber.SpringMVC.repositories.CustomerRepository;
import com.indocyber.SpringMVC.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanServices {
    private LoanRepository loanRepository;
    private CustomerRepository customerRepository;
    private BookRepository bookRepository;

    @Autowired
    public LoanServices(LoanRepository loanRepository, CustomerRepository customerRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
    }



    private final int PAGE_LIMIT= 10;


    public Page<LoanGridDTO> findAllAuthor(Integer page, String name, String title) {
        Pageable pagination = PageRequest.of(page - 1, PAGE_LIMIT, Sort.by("id"));
        Page<LoanGridDTO> allDataLoan = loanRepository.findAll(name,title, pagination);
        return allDataLoan;
    }

    public UpsertLoanDTO findById(Long id){
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id Loan tidak ditemukan"));
        return new UpsertLoanDTO(
                loan.getId(),
                loan.getCustomerNumber().getId(),
                loan.getBookCode().getId(),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnDate(),
                loan.getNote()
        );
    }

    public void saveDataLoan(UpsertLoanDTO loanDto){
        Customer customer = customerRepository.findById(loanDto.getCustomerNumber())
                .orElseThrow(() -> new IllegalArgumentException("Customer dengan Id tersebut Tidak Ditemukan"));
        Book book = bookRepository.findById(loanDto.getBookCode())
                .orElseThrow(() -> new IllegalArgumentException("Book dengan Id tersebut Tidak Ditemukan"));

        Loan loan = new Loan(
                loanDto.getId(),
                customer,
                book,
                loanDto.getLoanDate(),
                loanDto.getLoanDate().plusDays(5),
                loanDto.getReturnDate(),
                loanDto.getNote()
        );
        loanRepository.save(loan);
        book.setIsBorrowed("1");
        bookRepository.save(book);
    }

    public void updateLoan(UpsertLoanDTO loan) {
        customerRepository.findById(loan.getCustomerNumber())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        bookRepository.findById(loan.getBookCode())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        Loan updateLoan = loanRepository.findById(loan.getId())
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));

        updateLoan.setLoanDate(loan.getLoanDate());
        updateLoan.setDueDate(loan.getLoanDate().plusDays(5));
        updateLoan.setNote(loan.getNote());
        loanRepository.save(updateLoan);
    }

    public List<Book> findBookByLoanId(Long id){return bookRepository.findByLoans_Id(id);}

    public void returnBook(Long id) {
        Loan data = loanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));
        Book book = data.getBookCode();
        book.setIsBorrowed("0");
        bookRepository.save(book);
        data.setReturnDate(LocalDate.now());
        loanRepository.save(data);
    }

    public LoanDetailDTO loanDetail(Long id){
        LoanDetailDTO loan = loanRepository.findLoanById(id);
        return loan;
    }
}
