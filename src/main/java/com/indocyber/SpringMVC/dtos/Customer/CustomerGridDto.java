package com.indocyber.SpringMVC.dtos.Customer;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class CustomerGridDto{
    private final String id;
    private final String fullName;
    private final LocalDate birthDate;
    private final String gender;
    private final String phone;
    private final String address;
    private final LocalDate membershipExpireDate;
}
