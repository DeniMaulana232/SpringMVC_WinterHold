package com.indocyber.SpringMVC.controllers;

import com.indocyber.SpringMVC.dtos.Books.BookDto;
import com.indocyber.SpringMVC.dtos.Customer.CustomerGridDto;
import com.indocyber.SpringMVC.dtos.Loan.LoanGridDTO;
import com.indocyber.SpringMVC.dtos.Loan.UpsertLoanDTO;
import com.indocyber.SpringMVC.models.Book;
import com.indocyber.SpringMVC.services.CategoryService;
import com.indocyber.SpringMVC.services.CustomerService;
import com.indocyber.SpringMVC.services.LoanServices;
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
@RequestMapping("loan")
public class LoanController {
    private LoanServices services;
    private CustomerService customerService;
    private CategoryService categoryService;

    @Autowired
    public LoanController(LoanServices services, CustomerService customerService, CategoryService categoryService) {
        this.services = services;
        this.customerService = customerService;
        this.categoryService = categoryService;
    }




    @GetMapping("index")
    public String getAllLoanData(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "") String name,
                                 @RequestParam(defaultValue = "") String title,
                                 Model model){
        Page<LoanGridDTO> allLoanData = services.findAllAuthor(page,name,title);
        List<LoanGridDTO> loans = LoanGridDTO.convert(allLoanData.getContent());
        model.addAttribute("loan",loans);
        model.addAttribute("page",page);
        model.addAttribute("name",name);
        model.addAttribute("title",title);
        model.addAttribute("totalPage",allLoanData.getTotalPages());
        model.addAttribute("breadCrumbs", "LOAN / INDEX");

        return "loan/loan-index";
    }

    @GetMapping("upsert-form")
    public String upsertForm(@RequestParam(required = false) Long id, Model model) {
        List<CustomerGridDto> customers = customerService.findAllCustomerNotExp();
        model.addAttribute("customers", customers);
        if (id != null) {
            List<Book> books = services.findBookByLoanId(id);
            model.addAttribute("books", books);
            model.addAttribute("loan", services.findById(id));
            model.addAttribute("breadCrumbs", "LOAN / UPDATE LOAN");
        } else {
            List<BookDto> books = categoryService.findAllBookAlailable();
            model.addAttribute("books", books);
            model.addAttribute("loan", new UpsertLoanDTO());
            model.addAttribute("breadCrumbs", "LOAN / INSERT LOAN");
        }
        return "loan/loan-form";
    }

    @PostMapping("upsert")
    public String upsert(@Valid @ModelAttribute("loan") UpsertLoanDTO loan,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            List<CustomerGridDto> customers = customerService.findAllCustomerNotExp();
            List<BookDto> books = categoryService.findAllBookAlailable();
            model.addAttribute("customers", customers);
            model.addAttribute("books", books);
            model.addAttribute("loan", loan);
            return "loan/loan-upsert";
        }
        model.getAttribute("loan");
        if (loan.getId() != null) {
            services.updateLoan(loan);
            redirectAttributes.addFlashAttribute("success", "Update Loan Success");
        } else {
            services.saveDataLoan(loan);
            redirectAttributes.addFlashAttribute("success", "Insert Loan Success");
        }

        return "redirect:/loan/index";
    }

    @GetMapping("return")
    public String returnBook(@RequestParam(required = false) Long id,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (id != null) {
            services.returnBook(id);
            redirectAttributes.addFlashAttribute("success", "Return Book Success");
        }
        return "redirect:/loan/index";
    }

    @GetMapping("detail")
    public String detail(@RequestParam(required = false) Long id, Model model) {
        if (id != null) {
            model.addAttribute("loanDetail", services.loanDetail(id));
            model.addAttribute("breadCrumbs", "LOAN / DETAIL LOAN");
        }
        return "loan/loan-detail";
    }
}
