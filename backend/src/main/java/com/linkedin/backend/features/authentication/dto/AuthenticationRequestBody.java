package com.linkedin.backend.features.authentication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthenticationRequestBody {
    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    public AuthenticationRequestBody(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
