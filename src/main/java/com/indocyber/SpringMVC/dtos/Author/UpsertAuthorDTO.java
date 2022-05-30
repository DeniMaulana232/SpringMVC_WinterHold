package com.indocyber.SpringMVC.dtos.Author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertAuthorDTO {
    private  Long id;
    @NotNull(message="Title tidak boleh kosong")
    private String title;
    @NotNull(message="FirstName tidak boleh kosong")
    private String firstName;
    private String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message="Birth date tidak boleh kosong")
    private LocalDate birthDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deceasedDate;
    private String education;
    private String summary;

}
