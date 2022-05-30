package com.indocyber.SpringMVC.dtos.Author;

import com.indocyber.SpringMVC.models.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorBookGridDTO {
    private String id;
    private String title;
    private String categoryName;
    private String isBorrowed;
    private LocalDate releaseDate;
    private Integer totalPage;
//
    public static List<AuthorBookGridDTO> convert(List<AuthorBookGridDTO> books) {
        List<AuthorBookGridDTO> result = new ArrayList<>();
        for (AuthorBookGridDTO book : books) {
            result.add(new AuthorBookGridDTO(
                    book.getId(),
                    book.getTitle(),
                    book.getCategoryName(),
                    book.getIsBorrowed().equals("1") ? "Borrowed" : "Available",
                    book.getReleaseDate(),
                    book.getTotalPage()
            ));
        }
        return result;
    }
}
