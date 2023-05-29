package com.parcial2.controllers;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import org.hibernate.dialect.PostgreSQLCastingIntervalSecondJdbcType;
import org.postgresql.util.PGInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parcial2.models.dtos.MessageDTO;
import com.parcial2.models.dtos.SaveSongDTO;
import com.parcial2.models.entities.Song;
import com.parcial2.models.entities.User;
import com.parcial2.services.SongService;
import com.parcial2.utils.RequestErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/song")
public class SongController {
	
	@Autowired
	private  SongService songService;
	
	private RequestErrorHandler errorHandler;
	
	@PostMapping("/save")
	public ResponseEntity<?>saveSong(@RequestBody @Valid SaveSongDTO info, BindingResult validations){
		
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
			
			//El tipo de dato de duration se cambio por String ya que estaba teniendo muchos conflictos
			
			songService.save(info.getTitle(), info.getDuration());
			return new ResponseEntity<>(
					new MessageDTO("Song added" + info), HttpStatus.CREATED);
			
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(
					new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getSongById(@PathVariable(name = "id") UUID id) {
	    Song song = songService.findOneById(id);
	    
	    if(song == null) {
	    	return new ResponseEntity<>(
	    		new MessageDTO("Song not found"),
	    		HttpStatus.NOT_FOUND);
	    }
	    return new ResponseEntity<>(song, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSongById(@PathVariable(name = "id") UUID id) {
	    try {
	        songService.deleteById(id.toString());
	        return new ResponseEntity<>(
	                new MessageDTO("Song deleted"),
	                HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(
	                new MessageDTO("Internal Server Error"),
	                HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	@GetMapping("/songs")
	public ResponseEntity<?>findAllSongs(){
		List<Song> songs = songService.findAll();
		return new ResponseEntity<>(
					songs,
					HttpStatus.OK
				);
	}
}
