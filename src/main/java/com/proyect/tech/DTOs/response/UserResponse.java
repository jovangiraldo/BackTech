package com.proyect.tech.DTOs.response;

import com.proyect.tech.Model.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record UserResponse(
	Long id,
	String name,
	String firstLastName,
	String secondLastName,
	String idCard,
	LocalDateTime birthDate,
	Long authId,
	List<Long> contactIds,
	List<Long> petIds,
	Set<String> roles
) {

    public static UserResponse fromEntity(User user) {
	Long authId = user.getAuth() != null ? user.getAuth().getId() : null;

	List<Long> contactIds = user.getContacts() == null
		? List.of()
		: user.getContacts().stream().map(c -> c.getId()).toList();

	List<Long> petIds = user.getPets() == null
		? List.of()
		: user.getPets().stream().map(p -> p.getId()).toList();

	Set<String> roles = user.getRoles() == null
		? Set.of()
		: user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toSet());

	return new UserResponse(
		user.getId(),
		user.getName(),
		user.getFirstLastName(),
		user.getSecondLastName(),
		user.getIdCard(),
		user.getBirthDate(),
		authId,
		contactIds,
		petIds,
		roles
	);
    }
}
