package com.indocyber.SpringMVC.services;

import com.indocyber.SpringMVC.dtos.Author.AuthorDTO;
import com.indocyber.SpringMVC.dtos.Books.BookDto;
import com.indocyber.SpringMVC.dtos.Books.UpsertBookCategoryDTO;
import com.indocyber.SpringMVC.dtos.Category.CategoriGridDTO;
import com.indocyber.SpringMVC.dtos.Category.CategoryBookGridDTO;
import com.indocyber.SpringMVC.dtos.Category.UpsertCategoryDTO;
import com.indocyber.SpringMVC.models.Book;
import com.indocyber.SpringMVC.models.Category;
import com.indocyber.SpringMVC.repositories.AuthorRepository;
import com.indocyber.SpringMVC.repositories.BookRepository;
import com.indocyber.SpringMVC.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, BookRepository bookRepository, AuthorRepository authorRepository) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }



    private final int PAGE_LIMIT = 5;


    public Page<Category> getAllCategory(Integer page, String categoryName) {
//        Pageable pageable = PageRequest.of(page - 1, PAGE_LIMIT,Sort.by("Name"));
//        Page<Category> allCategory = categoryRepository.findAll(categoryName, pageable);

        return categoryRepository.findByCategoryNameContaining(categoryName, PageRequest.of(page - 1, PAGE_LIMIT, Sort.by("categoryName")));
    }

    public void saveCategory(UpsertCategoryDTO categoryDTO){
        Category category = new Category(
                categoryDTO.getCategoryName(),
                categoryDTO.getFloor(),
                categoryDTO.getIsle(),
                categoryDTO.getBay());
        categoryRepository.save(category);
    }

    public void saveCategoryBook(UpsertBookCategoryDTO bookCategoryDTO){
        Book book = new Book(
                bookCategoryDTO.getId(),
                bookCategoryDTO.getTitle(),
                bookCategoryDTO.getCategoryName(),
                bookCategoryDTO.getAuthorId(),
                bookCategoryDTO.getIsBorrowed(),
                bookCategoryDTO.getSummary(),
                bookCategoryDTO.getReleaseDate(),
                bookCategoryDTO.getTotalPage()
        );
        bookRepository.save(book);
    }

    public UpsertCategoryDTO getCategoryById(String id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category Not Found"));

        return new UpsertCategoryDTO(
                category.getCategoryName(),
                category.getFloor(),
                category.getIsle(),
                category.getBay());
    }

    public void deleteCategoryById(String id){categoryRepository.deleteById(id);}

    public Page<CategoryBookGridDTO> findAllCategoryBookGridDTO(String categoryName,String title, String id, Integer page){
        Pageable pageable = PageRequest.of(page - 1, PAGE_LIMIT,Sort.by("title"));
        Page<CategoryBookGridDTO> books = categoryRepository.findAllAuthorBookGridDTO(categoryName,title, id, pageable);
        return books;
    }

    public List<AuthorDTO> findAllAuthor(){
        List<AuthorDTO> authorDto =AuthorDTO.toList(authorRepository.findAll());
        return authorDto;
    }

    public List<BookDto> findAllBookAlailable() {
        return categoryRepository.findAllBookAvailable();
    }
}
