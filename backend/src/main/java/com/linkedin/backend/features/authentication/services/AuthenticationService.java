package com.linkedin.backend.features.authentication.services;

import com.linkedin.backend.features.authentication.dto.AuthenticationRequestBody;
import com.linkedin.backend.features.authentication.dto.AuthenticationResponseBody;
import com.linkedin.backend.features.authentication.model.AuthenticationUser;
import com.linkedin.backend.features.authentication.repository.AuthenticationUserRepository;
import com.linkedin.backend.features.authentication.utils.EmailService;
import com.linkedin.backend.features.authentication.utils.Encoder;
import com.linkedin.backend.features.authentication.utils.JsonWebToken;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthenticationService {
    private final Encoder encoder;
    private final AuthenticationUserRepository  authenticationUserRepository;
    private final JsonWebToken jsonWebToken;
    private final EmailService emailService;
    private int durationInMinutes = 10;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthenticationService(Encoder encoder, AuthenticationUserRepository authenticationUserRepository, JsonWebToken jsonWebToken, EmailService emailService) {
        this.encoder = encoder;
        this.authenticationUserRepository = authenticationUserRepository;
        this.jsonWebToken = jsonWebToken;
        this.emailService = emailService;
    }

    public AuthenticationUser getUser(String email){
        return authenticationUserRepository
                .findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public AuthenticationResponseBody register(AuthenticationRequestBody registerRequestBody) {
        authenticationUserRepository.save(new AuthenticationUser(registerRequestBody.getEmail(),encoder.encode(registerRequestBody.getPassword())));
        String token = jsonWebToken.generateToken(registerRequestBody.getEmail());
        return new AuthenticationResponseBody(token,"User registered successfully");
    }

    public AuthenticationResponseBody login(AuthenticationRequestBody loginRequestBody) {
        AuthenticationUser user = authenticationUserRepository
                .findByEmail(loginRequestBody.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if(!encoder.matches(loginRequestBody.getPassword(),user.getPassword())){
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = jsonWebToken.generateToken(user.getEmail());
        return new AuthenticationResponseBody(token,"User logged in successfully");
    }

    public void validateEmailVerificationToken(String token, String email) {
        AuthenticationUser user = authenticationUserRepository
                .findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (user.getEmailVerificationToken() == null) {
            throw new IllegalArgumentException("Email verification token not found");
        }
        if (user.getEmailVerificationTokenExpiryDate() == null) {
            throw new IllegalArgumentException("Email verification token expiry date not found");
        }
        if (user.getEmailVerified()) {
            throw new IllegalArgumentException("Email already verified");
        }

    }

    public String generateEmailVerificationToken(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<5;i++){
            SecureRandom random = new SecureRandom();
            stringBuilder.append(random.nextInt(9));
        }
        return stringBuilder.toString();
    }

    public void sendEmailVerificationToken(String email) {
        Optional<AuthenticationUser> user = authenticationUserRepository.findByEmail(email);
        if (user.isPresent() && !user.get().getEmailVerified()) {
            String emailVerificationToken = generateEmailVerificationToken();
            String hashedToken = encoder.encode(emailVerificationToken);
            user.get().setEmailVerificationToken(hashedToken);
            user.get().setEmailVerificationTokenExpiryDate(LocalDateTime.now().plusMinutes(durationInMinutes));
            authenticationUserRepository.save(user.get());
            String subject = "Email Verification";
            String body = String.format("Only one step to take full advantage of LinkedIn.\n\n"
                            + "Enter this code to verify your email: " + "%s\n\n" + "The code will expire in " + "%s" + " minutes.",
                    emailVerificationToken, durationInMinutes);
            try {
                emailService.sendEmail(email, subject, body);
            } catch (Exception e) {
                logger.info("Error while sending email: {}", e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("Email verification token failed, or email is already verified.");
        }
    }

    public void sendPasswordResetToken(String email) {
        Optional<AuthenticationUser> user = authenticationUserRepository.findByEmail(email);
        if (user.isPresent()) {
            String passwordResetToken = generateEmailVerificationToken();
            String hashedToken = encoder.encode(passwordResetToken);
            user.get().setPasswordResetToken(hashedToken);
            user.get().setPasswordResetTokenExpiryDate(LocalDateTime.now().plusMinutes(durationInMinutes));
            authenticationUserRepository.save(user.get());
            String subject = "Password Reset";
            String body = String.format("""
                            You requested a password reset.
                            
                            Enter this code to reset your password: %s. The code will expire in %s minutes.""",
                    passwordResetToken, durationInMinutes);
            try {
                emailService.sendEmail(email, subject, body);
            } catch (Exception e) {
                logger.info("Error while sending email: {}", e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("User not found.");
        }
    }

    public void resetPassword(String email, String newPassword, String token) {
        Optional<AuthenticationUser> user = authenticationUserRepository.findByEmail(email);
        if (user.isPresent() && encoder.matches(token, user.get().getPasswordResetToken()) && !user.get().getPasswordResetTokenExpiryDate().isBefore(LocalDateTime.now())) {
            user.get().setPasswordResetToken(null);
            user.get().setPasswordResetTokenExpiryDate(null);
            user.get().setPassword(encoder.encode(newPassword));
            authenticationUserRepository.save(user.get());
        } else if (user.isPresent() && encoder.matches(token, user.get().getPasswordResetToken()) && user.get().getPasswordResetTokenExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Password reset token expired.");
        } else {
            throw new IllegalArgumentException("Password reset token failed.");
        }
    }
}
