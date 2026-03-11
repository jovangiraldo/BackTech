package com.proyect.tech.Model;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.*;

@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, unique = true)
    private String identifier;       // identificador único de la mascota (chip, etc.)

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;             // perro, gato, etc.

    @Column(name = "year_old", nullable = true)
    private String yearOld;          // edad

    @Column(nullable = true, columnDefinition = "TEXT")
    private String observation;      // notas clínicas, puede ser largo

    // ── Relación N a 1 con Client ─────────────────────────
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;           // 👈 FK → clients.id

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ── Constructor vacío requerido por JPA ───────────────
    public Pet() {}

    // ── Getters y Setters ─────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIdentifier() { return identifier; }
    public void setIdentifier(String identifier) { this.identifier = identifier; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getYearOld() { return yearOld; }
    public void setYearOld(String yearOld) { this.yearOld = yearOld; }

    public String getObservation() { return observation; }
    public void setObservation(String observation) { this.observation = observation; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}