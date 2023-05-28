package com.parcial2.services;

import java.util.List;

import com.parcial2.models.dtos.SaveDTO;
import com.parcial2.models.entities.User;

public interface UserService {
	public void save(SaveDTO info) throws Exception;
	void deleteById(String id) throws Exception;
	User findOneById(String id);
	List<User> findAll();
	

}
