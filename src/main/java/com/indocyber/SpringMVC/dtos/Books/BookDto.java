package com.indocyber.SpringMVC.dtos.Books;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class BookDto implements Serializable {
    private final String id;
    private final String title;
    private final String isBorrowed;
    private final String summary;
    private final LocalDate releaseDate;
    private final Integer totalPage;
}
