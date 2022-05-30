package com.indocyber.SpringMVC.dtos.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertCategoryDTO {
    private String categoryName;
    private Integer floor;
    private String isle;
    private String bay;
}
