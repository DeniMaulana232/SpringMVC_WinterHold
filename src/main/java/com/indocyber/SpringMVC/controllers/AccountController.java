package com.indocyber.SpringMVC.controllers;

import com.indocyber.SpringMVC.dtos.Account.RegisterAccountDTO;
import com.indocyber.SpringMVC.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("account")
public class AccountController {

    private AccountService service;

    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping("login-form")
    public String login(Model model){
        return "account/login-form";
    }

    @RequestMapping(
            method={RequestMethod.GET,RequestMethod.POST}, path="/access-denied")
    public String accessDenied(){
        return "account/access-denied";
    }

    @GetMapping("register-form")
    public String registerForm(Model model) {
        RegisterAccountDTO dto = new RegisterAccountDTO();
        model.addAttribute("account", dto);
        return "account/register-form";
    }

    @PostMapping("register")
    public String register(@Valid @ModelAttribute("account") RegisterAccountDTO dto,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("account", dto);
            return "account/register-form";
        }
        service.registerAccount(dto);
        return "redirect:/account/login-form";
    }
}
