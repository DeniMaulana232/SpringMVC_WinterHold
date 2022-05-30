package com.indocyber.SpringMVC.dtos.Author;

import com.indocyber.SpringMVC.models.Author;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class AuthorDTO {
    private final Long id;
    private final String title;
    private final String fullName;
    private final LocalDate birthDate;
    private final LocalDate deceasedDate;
    private final String education;
    private final String summary;

    public static List<AuthorDTO> toList (List<Author> authors) {
        List<AuthorDTO> result = new ArrayList<>();

        for (Author author : authors) {
            result.add(new AuthorDTO(
                    author.getId(),
                    author.getTitle(),
                    author.getFullName(),
                    author.getBirthDate(),
                    author.getDeceasedDate(),
                    author.getEducation(),
                    author.getSummary()));
        }
        return result;
    }
}
