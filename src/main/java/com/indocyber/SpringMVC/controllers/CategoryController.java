package com.indocyber.SpringMVC.controllers;

import com.indocyber.SpringMVC.dtos.Books.UpsertBookCategoryDTO;
import com.indocyber.SpringMVC.dtos.Category.CategoriGridDTO;
import com.indocyber.SpringMVC.dtos.Category.CategoryBookGridDTO;
import com.indocyber.SpringMVC.dtos.Category.UpsertCategoryDTO;
import com.indocyber.SpringMVC.models.Category;
import com.indocyber.SpringMVC.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {
    private CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("index")
    public String getAllCategory(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue="") String categoryName,
                                 Model model) {
        Page<Category> allCategory = service.getAllCategory(page, categoryName);
        List<CategoriGridDTO> categoryDto = CategoriGridDTO.convert(allCategory.getContent());
        model.addAttribute("category", categoryDto);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", allCategory.getTotalPages());
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("breadCrumbs", "Category Index");

        return "category/category-index";
    }

    @GetMapping("upsert-form")
    public String upsertForm(@RequestParam(required = false) String id, Model model){
        if(id != null){
            model.addAttribute("category", service.getCategoryById(id));
            model.addAttribute("type", "update");
            model.addAttribute("breadCrumbs", "Category Update");
        }else {
            model.addAttribute("type", "insert");
            model.addAttribute("category", new UpsertCategoryDTO());
            model.addAttribute("breadCrumbs","Category Insert");
        }
        return "category/category-form";
    }

    @PostMapping("update")
    public String update(@Valid UpsertCategoryDTO category,
                             BindingResult bindingResult,
                             Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("category",model);
            return "category/upsert-form";
        }
        service.saveCategory(category);
        return "redirect:/category/index";
    }
    @PostMapping("insert")
    public String insert(@Valid UpsertCategoryDTO category,
                             BindingResult bindingResult,
                             Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("category",model);
            return "category/upsert-form";
        }
        service.saveCategory(category);
        return "redirect:/category/index";
    }

    @PostMapping("delete")
    public String delete(@RequestParam String id){
        service.deleteCategoryById(id);
        return "redirect:/category/index";
    }

    @GetMapping("books")
    public String book(@RequestParam(defaultValue = "1") int page,
                       @RequestParam (defaultValue = "")String categoryName,
                       @RequestParam(defaultValue = "") String title,
                       @RequestParam(defaultValue = "") String id,
                       Model model){
        Page<CategoryBookGridDTO> allBooks = service.findAllCategoryBookGridDTO(categoryName,title, id, page);
        List<CategoryBookGridDTO> books = CategoryBookGridDTO.convert(allBooks.getContent());
        model.addAttribute("category", service.getCategoryById(categoryName));
        model.addAttribute("books", books);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", allBooks.getTotalPages());
        model.addAttribute("title", title);
        model.addAttribute("id", id);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("breadCrumbs", "Category Books");
        return "category/category-book";
    }

    @GetMapping("book-upsert-form")
    public String bookUpsertForm(@RequestParam(required = false) String id,
                                 @RequestParam String categoryName,
                                 Model model){
        UpsertBookCategoryDTO dto = new UpsertBookCategoryDTO();
        dto.setCategoryName(categoryName);
        model.addAttribute("categoryBook", dto);
        model.addAttribute("category", service.getCategoryById(categoryName));
        model.addAttribute("breadCrumbs", "Category Book Assign");
        model.addAttribute("dropdownAuthor", service.findAllAuthor());
        return "category/category-book-form";
    }

    @PostMapping("book-upsert")
    public String bookUpsertForm(@Valid @ModelAttribute("categoryBook") UpsertBookCategoryDTO dto,
                                 BindingResult bindingResult,
                                 Model model ){
        if (bindingResult.hasErrors()){
            model.addAttribute("categoryBook",dto);
            return "category/book-upsert-form";
        }
        service.saveCategoryBook(dto);
        return "redirect:/category/index";
    }
}
