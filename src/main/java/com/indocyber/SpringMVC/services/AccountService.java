package com.indocyber.SpringMVC.services;

import com.indocyber.SpringMVC.configs.AppSecurityConfig;
import com.indocyber.SpringMVC.dtos.Account.RegisterAccountDTO;
import com.indocyber.SpringMVC.models.Account;
import com.indocyber.SpringMVC.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void registerAccount(RegisterAccountDTO regisDto) {
        PasswordEncoder passwordEncoder = AppSecurityConfig.passwordEncoder();
        String hashPassword = passwordEncoder.encode(regisDto.getPassword());
        accountRepository.save(new Account(
                regisDto.getUsername(),
                hashPassword));
    }

}
