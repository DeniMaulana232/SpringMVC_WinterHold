package com.indocyber.SpringMVC.dtos.Loan;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanDetailDTO {
    private final Long id;
    private final String customerNumber;
    private final String title;
    private final String categoryName;
    private final String fullNameAuthor;
    private final String phone;
    private final String fullNameCustomer;
    private final LocalDate membershipExpireDate;

    private final Integer floor;
    private final String isle;
    private final String bay;

}
