package com.indocyber.SpringMVC.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Category")
@Getter
@Setter
public class Category {
    @Id
    @Column(name = "Name", nullable = false, length = 100)
        private String categoryName;

    @Column(name = "Floor", nullable = false)
    private Integer floor;

    @Column(name = "Isle", nullable = false, length = 10)
    private String isle;

    @Column(name = "Bay", nullable = false, length = 10)
    private String bay;

    @OneToMany(mappedBy = "categoryName")
    private Set<Book> books = new LinkedHashSet<>();

    public Category(String categoryName, Integer floor, String isle, String bay) {
        this.categoryName = categoryName;
        this.floor = floor;
        this.isle = isle;
        this.bay = bay;
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
}