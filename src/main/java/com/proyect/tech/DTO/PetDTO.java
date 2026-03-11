package com.proyect.tech.DTO;

import com.proyect.tech.Model.Pet;

public class PetDTO {
    private Long id;
    private String identifier;
    private String name;
    private String type;
    private String yearOld;
    private String observation;
    private Long clientId;
    private String clientName;
    private String clientEmail;
    private String clientPhone;
    private String clientAddress;

    public PetDTO() {
    }

    public PetDTO(Pet pet) {
        this.id = pet.getId();
        this.identifier = pet.getIdentifier();
        this.name = pet.getName();
        this.type = pet.getType();
        this.yearOld = pet.getYearOld();
        this.observation = pet.getObservation();
        this.clientId = pet.getClient().getId();
        this.clientName = pet.getClient().getUser().getName();
        this.clientEmail = pet.getClient().getUser().getEmail();
        this.clientPhone = pet.getClient().getUser().getPhone();
        this.clientAddress = pet.getClient().getUser().getAddress();
    }

    public Long getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getYearOld() {
        return yearOld;
    }

    public String getObservation() {
        return observation;
    }

    public Long getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setYearOld(String yearOld) {
        this.yearOld = yearOld;
    }

    public void setObservation(String obs) {
        this.observation = obs;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    public String getClientEmail() {
        return clientEmail;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }


}