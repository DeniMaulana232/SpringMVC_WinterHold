package com.indocyber.SpringMVC.dtos.Author;

import lombok.Data;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Data
public class AuthorGridDTO {
    private final Long id;
    private final String fullName;
    private final long age;
    private final String status;
    private final String education;

    public static List<AuthorGridDTO> toList (List<AuthorDTO> AuthorDtos) {
        List<AuthorGridDTO> result = new ArrayList<>();

        for (AuthorDTO authorDto : AuthorDtos   ) {
            long age ;
            if (authorDto.getDeceasedDate() == null) {
                age  = ChronoUnit.YEARS.between(authorDto.getBirthDate(), LocalDate.now());
            } else{
                age  = ChronoUnit.YEARS.between(authorDto.getBirthDate(), authorDto.getDeceasedDate());
            }
            result.add(new AuthorGridDTO(
                    authorDto.getId(),
                    authorDto.getFullName(),
                    age,
                    authorDto.getDeceasedDate() == null ? "Alive" : "Deceased",
                    authorDto.getEducation()));
        }
        return result;
    }
}
