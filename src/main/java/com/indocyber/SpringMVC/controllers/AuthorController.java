package com.indocyber.SpringMVC.controllers;

import com.indocyber.SpringMVC.dtos.Author.AuthorBookGridDTO;
import com.indocyber.SpringMVC.dtos.Author.AuthorDTO;
import com.indocyber.SpringMVC.dtos.Author.AuthorGridDTO;
import com.indocyber.SpringMVC.dtos.Author.UpsertAuthorDTO;
import com.indocyber.SpringMVC.models.Book;
import com.indocyber.SpringMVC.services.AuthorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("author")
public class AuthorController {
    private AuthorServices services;

    @Autowired
    public AuthorController(AuthorServices services) {
        this.services = services;
    }

    @GetMapping("home")
    public String home(){
        return "home";
    }

    @GetMapping("index")
    public String index(@RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "") String fullName,
                        Model model) {
        Page<AuthorDTO> allAuthor = services.findAllAuthor(page, fullName);
        List<AuthorGridDTO> authorGrid = AuthorGridDTO.toList(allAuthor.getContent());
        model.addAttribute("author", authorGrid);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", allAuthor.getTotalPages());
        model.addAttribute("fullName", fullName);
        model.addAttribute("breadCrumbs", "AUTHOR / INDEX");
        return "author/author-index";
    }

    @GetMapping("upsert-form")
    public String upsertForm(@RequestParam(required = false) Long id, Model model) {
        if (id != null) {
            model.addAttribute("author", services.getAuthorById(id));
            model.addAttribute("breadCrumbs", "AUTHOR / UPDATE AUTHOR");
        } else {
            model.addAttribute("author", new UpsertAuthorDTO());
            model.addAttribute("breadCrumbs", "AUTHOR / INSERT AUTHOR");
        }
        return "author/author-form";
    }

    @PostMapping("upsert")
    public String Upsert(@Valid @ModelAttribute("author")  UpsertAuthorDTO author,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("author", author);
            return "author/author-form";
        }
        services.saveDataAuthor(author);
        return "redirect:/author/index";
    }

    @PostMapping("delete")
    public String delete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
//        if (id != null) {
//            try {
//                services.deleteAuthor(id);
//                redirectAttributes.addFlashAttribute("success", "Author has been deleted");
//            } catch (Exception e) {
//                redirectAttributes.addFlashAttribute("error", "Author has been used");
//            }
//        }
//        services.deleteAuthor(id);
//        return "redirect:/author/index";
        services.deleteAuthor(id);
        return "redirect:/author/index";
    }

    @GetMapping("books")
    public String book(@RequestParam Long id,
                       Model model) {

        List<AuthorBookGridDTO> allBook = services.findAllAuthorBookGridDTO(id);

        List<AuthorBookGridDTO>  books = AuthorBookGridDTO.convert(allBook);
        model.addAttribute("author", services.getAuthorById(id));
        model.addAttribute("book", books);
        model.addAttribute("breadCrumbs", "AUTHOR / BOOK");
        return "author/author-book";
    }

}
