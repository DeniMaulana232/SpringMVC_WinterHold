package com.indocyber.SpringMVC.dtos.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryBookGridDTO {
    private String id;
    private String title;
    private String categoryName;
    private String isBorrowed;
    private LocalDate releaseDate;
    private Integer totalPage;

    public static List<CategoryBookGridDTO> convert(List<CategoryBookGridDTO> books) {
        List<CategoryBookGridDTO> result = new ArrayList<>();
        for (CategoryBookGridDTO book : books) {
            result.add(new CategoryBookGridDTO(
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
