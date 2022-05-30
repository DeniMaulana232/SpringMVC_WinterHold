package com.indocyber.SpringMVC.dtos.Author;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AuthorConvertDTO {
    private String id;
    private String title;
    private String categoryName;
    private String isBorrowed;
    private LocalDate releaseDate;
    private Integer totalPage;


}
