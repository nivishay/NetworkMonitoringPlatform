package com.nivishay.nmp.users.domain;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User{
    @Id
    private UUID id;
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    @Enumerated (EnumType.STRING)
    private UserRole role;

    @Column(nullable = false)
    private Instant createdAt;

    protected User(){}
    public User(String email, String passwordHash, UserRole role) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.createdAt = Instant.now();
    }
    public UUID getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public UserRole getRole() {
        return role;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
}