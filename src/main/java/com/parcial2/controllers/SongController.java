package com.parcial2.controllers;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parcial2.models.dtos.MessageDTO;
import com.parcial2.models.dtos.SaveSongDTO;
import com.parcial2.services.SongService;
import com.parcial2.utils.RequestErrorHandler;

@RestController
@RequestMapping("/song")
public class SongController {
	
	@Autowired
	private  SongService songService;
	
	private RequestErrorHandler errorHandler;
	
	@PostMapping("/save")
	public ResponseEntity<?>saveSong(@RequestBody SaveSongDTO info, BindingResult validations){
		
		if(validations.hasErrors()) {
			System.out.println(info);
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), 
					HttpStatus.BAD_REQUEST);
			
		}
		try {
			String[] parts = info.getDuration().split(":");
			int minutes = Integer.parseInt(parts[0]);
			int seconds = Integer.parseInt(parts[1]);
			
			Duration duration = Duration.ofMinutes(minutes).plusSeconds(seconds);
			
			songService.save(info.getTitle(), duration);
			return new ResponseEntity<>(
					new MessageDTO("Song added" + info), HttpStatus.CREATED);
			
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(
					new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
