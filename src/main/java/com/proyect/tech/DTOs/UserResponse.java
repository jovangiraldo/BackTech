package com.proyect.tech.DTOs;

import com.proyect.tech.Model.User;
import java.time.LocalDateTime;
import java.util.List;

public record UserResponse(
	Long id,
	String name,
	String firstLastName,
	String secondLastName,
	String idCard,
	LocalDateTime birthDate,
	Long authId,
	List<Long> contactIds,
	List<Long> petIds
) {

    public static UserResponse fromEntity(User user) {
	Long authId = user.getAuth() != null ? user.getAuth().getId() : null;

	List<Long> contactIds = user.getContacts() == null
		? List.of()
		: user.getContacts().stream().map(c -> c.getId()).toList();

	List<Long> petIds = user.getPets() == null
		? List.of()
		: user.getPets().stream().map(p -> p.getId()).toList();

	return new UserResponse(
		user.getId(),
		user.getName(),
		user.getFirstLastName(),
		user.getSecondLastName(),
		user.getIdCard(),
		user.getBirthDate(),
		authId,
		contactIds,
		petIds
	);
    }
}
