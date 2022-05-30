package com.indocyber.SpringMVC.dtos.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterAccountDTO {
    @NotBlank(message = "username tidak boleh kosong")
    public String username;
    @NotBlank(message = "password tidak boleh kosong")
    public String password;

}
