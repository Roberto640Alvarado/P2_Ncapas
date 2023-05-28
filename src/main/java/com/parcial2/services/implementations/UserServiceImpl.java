package com.parcial2.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parcial2.models.dtos.SaveDTO;
import com.parcial2.models.entities.User;
import com.parcial2.repositories.UserRepository;
import com.parcial2.services.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void save(SaveDTO info) throws Exception {
		User newUser = new User(
					info.getUsername(),
					info.getEmail(),
					info.getPassword()
				);
		
		userRepository.save(newUser);
	}

	@Override
	public void deleteById(String id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User findOneById(String id) {
		try {
			UUID code = UUID.fromString(id);
			return userRepository.findById(code)
					.orElse(null);
			
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

}
