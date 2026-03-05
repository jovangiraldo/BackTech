package com.proyect.tech.Repository;

import com.proyect.tech.Model.Contact;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {

	List<Contact> findAllByUserId(Long userId);
}
