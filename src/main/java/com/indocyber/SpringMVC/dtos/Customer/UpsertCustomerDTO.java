package com.indocyber.SpringMVC.dtos.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertCustomerDTO {
    @NotBlank(message = "CustomerNumber tidak boleh kosong")
    private String id;

    @NotBlank(message = "FirstName tidak boleh kosong")
    private String firstName;

    @NotBlank(message = "LastName tidak boleh kosong")
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message="Birth date tidak boleh kosong")
    private LocalDate birthDate;

    @NotBlank(message = "gender tidak boleh kosong")
    private String gender;
    private String phone;
    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message="Birth date tidak boleh kosong")
    private LocalDate membershipExpireDate = LocalDate.now().plusYears(2);
}
