package com.indocyber.SpringMVC.dtos.Category;

import com.indocyber.SpringMVC.models.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriGridDTO implements Serializable {
    private String categoryName;
    private Integer floor;
    private String isle;
    private String bay;
    private Integer books;

    public static List<CategoriGridDTO> convert(List<Category> category) {
        List<CategoriGridDTO> result = new ArrayList<>();
        for (Category categories : category) {
            result.add(new CategoriGridDTO(
                    categories.getCategoryName(),
                    categories.getFloor(),
                    categories.getIsle(),
                    categories.getBay(),
                    categories.getBooks() == null ? 0 : categories.getBooks().size()
            ));
        }
        return result;
    }
}
