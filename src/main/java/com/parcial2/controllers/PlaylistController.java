package com.parcial2.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parcial2.models.dtos.MessageDTO;
import com.parcial2.models.dtos.SavePlaylistDTO;
import com.parcial2.models.entities.Playlist;
import com.parcial2.services.PlaylistService;
import com.parcial2.services.UserService;
import com.parcial2.utils.RequestErrorHandler;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {
    
    @Autowired
    private PlaylistService playlistService;
    
    @Autowired
    private UserService userService;
    
    private RequestErrorHandler errorHandler;
    
    @PostMapping("/save")
public ResponseEntity<?> savePlaylist(@RequestBody SavePlaylistDTO dto, BindingResult validations) {
    if (validations.hasErrors()) {
        return new ResponseEntity<>(
                errorHandler.mapErrors(validations.getFieldErrors()),
                HttpStatus.BAD_REQUEST);
    }
    
    try {
        // Verificar si el userCode es nulo o vacío
        String userCodeString = dto.getUser_code();
        if (userCodeString == null || userCodeString.isEmpty()) {
            return new ResponseEntity<>(
                    new MessageDTO("User code is required"),
                    HttpStatus.BAD_REQUEST);
        }
        
        // Verificar si el usuario existe
        UUID userCode = UUID.fromString(userCodeString);
        if (userService.findOneById(userCode) == null) {
            return new ResponseEntity<>(
                    new MessageDTO("User not found"),
                    HttpStatus.NOT_FOUND);
        }
        
        // Guardar la playlist
        playlistService.save(dto);
        return new ResponseEntity<>(
                new MessageDTO("Playlist created"),
                HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
        return new ResponseEntity<>(
                new MessageDTO("Invalid user code format"),
                HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>(
                new MessageDTO("Internal Server Error"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    
    @GetMapping("/{id}")
    public ResponseEntity<?> getPlaylistById(@PathVariable(name = "id") String code) {
        try {
            UUID playlistCode = UUID.fromString(code);
            Playlist playlist = playlistService.findOneById(playlistCode);
            
            if (playlist == null) {
                return new ResponseEntity<>(
                        new MessageDTO("Playlist not found"),
                        HttpStatus.NOT_FOUND);
            }
            
            return new ResponseEntity<>(playlist, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                    new MessageDTO("Invalid playlist ID format"),
                    HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/playlists")
    public ResponseEntity<?> getAllPlaylists() {
        List<Playlist> playlists = playlistService.findAll();
        return new ResponseEntity<>(playlists, HttpStatus.OK);
    }
}
