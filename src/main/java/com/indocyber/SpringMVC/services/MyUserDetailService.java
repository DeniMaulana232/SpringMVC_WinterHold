package com.indocyber.SpringMVC.services;

import com.indocyber.SpringMVC.models.Account;
import com.indocyber.SpringMVC.models.MyUserDetails;
import com.indocyber.SpringMVC.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
    private AccountRepository accountRepository;

    @Autowired
    public MyUserDetailService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findById(username)
                .orElseThrow(()-> new UsernameNotFoundException("Username tidak ditemukan"));
        return new MyUserDetails(account);
    }
}
