package com.indocyber.SpringMVC.repositories;

import com.indocyber.SpringMVC.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
