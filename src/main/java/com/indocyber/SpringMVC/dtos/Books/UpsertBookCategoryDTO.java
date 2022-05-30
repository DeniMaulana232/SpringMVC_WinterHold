package com.indocyber.SpringMVC.dtos.Books;

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
public class UpsertBookCategoryDTO {

    @NotBlank(message = "id tidak boleh kosong")
    private String id;
    @NotBlank(message = "title tidak boleh kosong")
    private String title;
    private String categoryName;
    @NotNull(message = "Author tidak boleh kosong")
    private Long authorId;
    private String isBorrowed = "0";
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message="Birth date tidak boleh kosong")
    private LocalDate releaseDate;
    private String summary;
    private Integer totalPage;


}
