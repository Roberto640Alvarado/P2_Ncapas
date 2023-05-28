package com.parcial2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parcial2.utils.RequestErrorHandler;
import com.parcial2.models.dtos.MessageDTO;
import com.parcial2.models.dtos.SaveDTO;
import com.parcial2.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	private RequestErrorHandler errorHandler;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveUser(@RequestBody SaveDTO info,  BindingResult validations){
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
	

}
