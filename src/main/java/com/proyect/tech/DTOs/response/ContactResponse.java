package com.proyect.tech.DTOs.response;

import com.proyect.tech.Model.Contact;

public record ContactResponse(
        Long id,
        String phone,
        String address,
        Long userId
) {

    public static ContactResponse fromEntity(Contact contact) {
        Long userId = contact.getUser() != null ? contact.getUser().getId() : null;

        return new ContactResponse(
                contact.getId(),
                contact.getPhone(),
                contact.getAddress(),
                userId
        );
    }
}
