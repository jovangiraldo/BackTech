package com.proyect.tech.Controller;

import com.proyect.tech.DTOs.response.ContactResponse;
import com.proyect.tech.DTOs.request.CreateContactRequest;
import com.proyect.tech.DTOs.request.UpdateContactRequest;
import com.proyect.tech.Model.Contact;
import com.proyect.tech.Service.ContactService;
import com.proyect.tech.Service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.StreamSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;
    private final UserService userService;

    public ContactController(ContactService contactService, UserService userService) {
        this.contactService = contactService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<ContactResponse>> findAll() {
        List<ContactResponse> response = StreamSupport.stream(contactService.findAll().spliterator(), false)
                .map(ContactResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ContactResponse.fromEntity(contactService.findByIdOrThrow(id)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ContactResponse>> findByUserId(@PathVariable Long userId) {
        List<ContactResponse> response = contactService.findAllByUserId(userId).stream()
                .map(ContactResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ContactResponse> create(@Valid @RequestBody CreateContactRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ContactResponse.fromEntity(contactService.save(toContact(request))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateContactRequest request) {
        contactService.findByIdOrThrow(id);
        return ResponseEntity.ok(ContactResponse.fromEntity(contactService.save(toContact(id, request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contactService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private Contact toContact(CreateContactRequest request) {
        Contact contact = new Contact();
        contact.setPhone(request.phone());
        contact.setAddress(request.address());
        contact.setUser(userService.findByIdOrThrow(request.userId()));
        return contact;
    }

    private Contact toContact(Long id, UpdateContactRequest request) {
        Contact contact = toContact(new CreateContactRequest(request.phone(), request.address(), request.userId()));
        contact.setId(id);
        return contact;
    }
}

