package com.parcial2.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parcial2.utils.RequestErrorHandler;

import jakarta.validation.Valid;

import com.parcial2.models.dtos.MessageDTO;
import com.parcial2.models.dtos.PasswordDTO;
import com.parcial2.models.entities.Song;
import com.parcial2.models.entities.User;
import com.parcial2.models.dtos.SaveDTO;
import com.parcial2.models.dtos.SaveSongDTO;
import com.parcial2.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	private RequestErrorHandler errorHandler;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveUser(@RequestBody @Valid SaveDTO info,  BindingResult validations){
		if(validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), 
					HttpStatus.BAD_REQUEST);
		}
		try {
			userService.save(info);
			return new ResponseEntity<>(
					new MessageDTO("User created" + info), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(
					new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	    
	}
	@GetMapping("/{id}")
public ResponseEntity<?> getUserById(@PathVariable(name = "id") UUID id) {
    User user = userService.findOneById(id);

    if (user == null) {
        return new ResponseEntity<>(
                new MessageDTO("User not found"),
                HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(user, HttpStatus.OK);
}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable(name = "id") UUID id) {
	    try {
	        userService.deleteById(id.toString());
	        return new ResponseEntity<>(
	                new MessageDTO("User deleted"),
	                HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(
	                new MessageDTO("Internal Server Error"),
	                HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@GetMapping("/users")
	public ResponseEntity<?> findAllUsers(){
		List<User> users = userService.findAll();
		return new ResponseEntity<>(users,HttpStatus.OK);
	}
	
	@PatchMapping("/update/{id}")
	public ResponseEntity<?>updatePassword(@PathVariable(name = "id") UUID id, @RequestBody PasswordDTO info, BindingResult validations){
			User user = userService.findOneById(id);
			
			if(validations.hasErrors()) {
				return new ResponseEntity<>(
						errorHandler.mapErrors(validations.getFieldErrors()), 
						HttpStatus.BAD_REQUEST);
				
			}
		    if(user == null) {
		    	return new ResponseEntity<>(
		    		new MessageDTO("User not found"),
		    		HttpStatus.NOT_FOUND);
		    }
		    try {
				userService.changePassword(id,info);
				return new ResponseEntity<>(
						new MessageDTO("Password update" ), HttpStatus.CREATED);
			}catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(
						new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	

}
