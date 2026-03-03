package com.nivishay.nmp.targets.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "targets")
public class Target {
    @Id
    private UUID id;

    @Column(name = "owner_user_id", nullable = false)
    private UUID ownerUserId;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String type; // DOMAIN / IP

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected Target() {
        // JPA
    }

    public Target(UUID ownerUserId, String url, String type) {
        this.id = UUID.randomUUID();
        this.ownerUserId = ownerUserId;
        this.url = url;
        this.type = type;
        this.createdAt = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getOwnerUserId() { return ownerUserId; }
    public String getUrl() { return url; }
    public String getType() { return type; }
    public Instant getCreatedAt() { return createdAt; }
}