package com.parcial2.repositories;

import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.parcial2.models.dtos.SaveDTO;
import com.parcial2.models.entities.User;

public interface UserRepository extends ListCrudRepository<User, UUID> {
	User findByUsernameOrEmail(String username, String email);	
	User save(SaveDTO info);

}
