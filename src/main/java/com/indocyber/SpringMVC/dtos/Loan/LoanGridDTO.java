package com.indocyber.SpringMVC.dtos.Loan;

import com.indocyber.SpringMVC.models.Loan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanGridDTO {
    private Long id;
    private String customerNumber;
    private String bookCode;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public static List<LoanGridDTO> convert(List<LoanGridDTO> loans) {
        List<LoanGridDTO> result = new ArrayList<>();
        for (LoanGridDTO loan : loans) {
            result.add(new LoanGridDTO(
                    loan.getId(),
                    loan.getCustomerNumber(),
                    loan.getBookCode(),
                    loan.getLoanDate(),
                    loan.getDueDate(),
                    loan.getReturnDate() == null ? null : loan.getReturnDate()
            ));
        }
        return result;
    }
}
