package com.parcial2.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parcial2.models.dtos.PasswordDTO;
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
	@Transactional(rollbackOn = Exception.class)
	public void deleteById(String id) throws Exception {
	    try {
	        UUID userId = UUID.fromString(id);
	        userRepository.deleteById(userId);
	    } catch (IllegalArgumentException e) {
	        // Si el ID no es válido
	        throw new Exception("Invalid song ID");
	    } catch (Exception e) {
	        // Manejar otras excepciones
	        throw new Exception("Failed to delete user");
	    }
	}

	@Override
public User findOneById(UUID id) {
    try {
        return userRepository.findById(id)
                .orElse(null);
    } catch (Exception e) {
        return null;
    }
}


	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public void changePassword(UUID id, PasswordDTO info)throws Exception {
		User user = findOneById(id);
		System.out.println(info.getOldPassword());
		if(user != null) {
			//Password exists
			if(user.getPassword().equals(info.getOldPassword())) {
				user.setPassword(info.getNewPassword());
				userRepository.save(user);
			}
			else {
				throw new Exception("Contraseña es distinta");
			}
		}
		else {
			throw new Exception("User not found papito");
		}	
	}

}
