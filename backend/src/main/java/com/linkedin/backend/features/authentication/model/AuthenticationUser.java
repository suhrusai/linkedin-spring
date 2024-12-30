package com.linkedin.backend.features.authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linkedin.backend.features.feed.model.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "users")
public class AuthenticationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Email
    @Column(unique = true)
    private String email;
    private Boolean emailVerified = false;
    private String emailVerificationToken = null;
    private LocalDateTime emailVerificationTokenExpiryDate = null;
    @JsonIgnore
    private String password;
    private String passwordResetToken = null;
    private LocalDateTime passwordResetTokenExpiryDate = null;

    private String firstName;
    private String lastName;
    private String company;
    private String position;
    private String location;

    public String getProfilePicture() {
        return profilePicture;
    }

    private String profilePicture;
    private Boolean profileComplete = false;

    @JsonIgnore
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "author"
    )
    private List<Post> posts;

    private void updateProfileCompletionStatus(){
        this.profileComplete = (this.firstName != null && this.lastName != null && this.profilePicture != null && this.company != null && this.position != null && this.location != null);
    }

    public AuthenticationUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthenticationUser() {
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getEmail() {
        return email;
    }

    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    public LocalDateTime getEmailVerificationTokenExpiryDate() {
        return emailVerificationTokenExpiryDate;
    }

    public void setEmailVerificationTokenExpiryDate(LocalDateTime emailVerificationTokenExpiryDate) {
        this.emailVerificationTokenExpiryDate = emailVerificationTokenExpiryDate;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public LocalDateTime getPasswordResetTokenExpiryDate() {
        return passwordResetTokenExpiryDate;
    }

    public void setPasswordResetTokenExpiryDate(LocalDateTime passwordResetTokenExpiryDate) {
        this.passwordResetTokenExpiryDate = passwordResetTokenExpiryDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
        updateProfileCompletionStatus();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        updateProfileCompletionStatus();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        updateProfileCompletionStatus();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
        updateProfileCompletionStatus();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
        updateProfileCompletionStatus();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
        updateProfileCompletionStatus();
    }

    public void setProfileComplete(Boolean profileComplete) {
        this.profileComplete = profileComplete;
    }

    public Boolean getProfileComplete() {
        return profileComplete;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        updateProfileCompletionStatus();
    }
}