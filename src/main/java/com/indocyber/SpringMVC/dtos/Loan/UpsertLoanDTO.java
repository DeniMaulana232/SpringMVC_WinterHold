package com.indocyber.SpringMVC.dtos.Loan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertLoanDTO {
    private Long id;
    @NotBlank(message = "Customer Number tidak boleh kosong")
    private String customerNumber;
    @NotBlank(message = "Book Code tidak boleh kosong")
    private String bookCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message="Loan Date tidak boleh kosong")
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String note;
}
