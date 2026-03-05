package com.proyect.tech.Service;

import com.proyect.tech.Model.Contact;
import java.util.List;

public interface ContactService {

    Iterable<Contact> findAll();

    Contact findByIdOrThrow(Long id);

    Contact save(Contact contact);

    void deleteById(Long id);

    boolean existsById(Long id);

    List<Contact> findAllByUserId(Long userId);
}
