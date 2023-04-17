package com.codewithfahim.expensetracker.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserModel {
    @NotBlank(message = "Username should not be null")
    @Size(min = 3, message = "Username should be atleast 3 characters")
    private String username;
    @NotNull(message = "Email should not be null")
    @Email(message = "Please enter a valid email")
    private String email;
    @NotNull(message = "Age should not be null")
    private Integer age;
    @NotNull(message = "Password should not be null")
    @Size(min = 8, message = "Password should be atleast 8 characters")
    private String password;
}