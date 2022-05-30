package com.indocyber.SpringMVC.controllers;

import com.indocyber.SpringMVC.dtos.Customer.CustomerGridDto;
import com.indocyber.SpringMVC.dtos.Customer.UpsertCustomerDTO;
import com.indocyber.SpringMVC.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("customer")
public class CustomerController {
    private CustomerService service;

    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("index")
    public String getAllCustomer(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "") String id,
                                 @RequestParam(defaultValue = "") String name,
                                 Model model){
        Page<CustomerGridDto> allCustomer = service.findAllCustomer(id, name,page);
        model.addAttribute("customer", allCustomer);
        model.addAttribute("id",id);
        model.addAttribute("name",name);
        model.addAttribute("page",page);
        model.addAttribute("totalPage", allCustomer.getTotalPages());
        model.addAttribute("breadCrumbs", "CUSTOMER / INDEX");

        return "customer/customer-index";
    }

    @GetMapping("upsert-form")
    public String upsertForm(@RequestParam(required = false) String id, Model model){
        if (id != null){
            model.addAttribute("customer", service.getCustomerById(id));
            model.addAttribute("type", "update");
            model.addAttribute("breadCrumbs", "CUSTOMER / UPDATE");
        }else {
            model.addAttribute("customer", new UpsertCustomerDTO());
            model.addAttribute("type", "insert");
            model.addAttribute("breadCrumbs", "CUSTOMER / INSERT");
        }
        return "customer/customer-form";
    }

    @PostMapping("update")
    public String upsert(@Valid @ModelAttribute("customer") UpsertCustomerDTO customer,
                         BindingResult bindingResult,
                         Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("customer", customer);
            return "customer/customer-form";
        }
        service.saveCustomer(customer);
        return "redirect:/customer/index";
    }

    @PostMapping("insert")
    public String insert(@Valid @ModelAttribute("customer") UpsertCustomerDTO customer,
                         BindingResult bindingResult,
                         Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("customer", customer);
            return "customer/customer-form";
        }
        service.saveCustomer(customer);
        return "redirect:/customer/index";
    }

    @GetMapping("extend")
    public String extend(@RequestParam String id){
        service.extendMembership(id);
        return "redirect:/customer/index";
    }

    @PostMapping("delete")
    public String delete(@RequestParam String id){
        service.deleteCustomerById(id);
        return "redirect:/customer/index";
    }

    @GetMapping("customer-detail")
    public String detail(@RequestParam(required = false) String id, Model model){
        if (id != null){
            model.addAttribute("customerDetail", service.findAllCustomerByIdForDetail(id));
            model.addAttribute("breadCrumbs", "CUSTOMER / DETAIL");
        }
        return "customer/customer-detail";
    }
}
