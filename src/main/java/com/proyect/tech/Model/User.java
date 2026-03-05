package com.proyect.tech.Model;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Generated(value = "com.proyect.tech.Model.User")
    private Long id;
    private String name;
    private String firstLastName;
    private String secondLastName;

    @Column(unique = true)
    private String idCard;

    private LocalDateTime birthDate;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserAuth auth;
 
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Contact> contacts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Pet> user;

    
}
