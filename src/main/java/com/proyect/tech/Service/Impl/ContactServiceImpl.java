package com.proyect.tech.Service.Impl;

import com.proyect.tech.Model.Contact;
import com.proyect.tech.Repository.ContactRepository;
import com.proyect.tech.Service.ContactService;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Contact> findAll() {
        return contactRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Contact findByIdOrThrow(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Contact not found with id: " + id));
    }

    @Override
    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public void deleteById(Long id) {
        if (!contactRepository.existsById(id)) {
            throw new NoSuchElementException("Contact not found with id: " + id);
        }
        contactRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return contactRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contact> findAllByUserId(Long userId) {
        return contactRepository.findAllByUserId(userId);
    }
}
