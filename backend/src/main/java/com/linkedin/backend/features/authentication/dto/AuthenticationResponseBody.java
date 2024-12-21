package com.linkedin.backend.features.authentication.dto;

public class AuthenticationResponseBody {
    final private String token;
    final private String message;

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public AuthenticationResponseBody(String token, String message) {
        this.token = token;
        this.message = message;
    }
}
