package com.parcial2.controllers;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import com.parcial2.models.dtos.SavePlaylistXSongDTO;
import com.parcial2.models.dtos.SaveSongDTO;
import com.parcial2.models.entities.Playlist;
import com.parcial2.models.entities.PlaylistXSong;
import com.parcial2.models.entities.Song;
import com.parcial2.services.PlaylistService;
import com.parcial2.services.PlaylistXSongService;
import com.parcial2.services.SongService;
import com.parcial2.utils.RequestErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/playlist_song")
public class PlaylistXSongController {
	
	@Autowired
	private PlaylistXSongService playlistXSongService;
	
	@Autowired
	private PlaylistService playlistService;
	
	@Autowired
	private SongService songService;
	
	private RequestErrorHandler errorHandler;
	
	//Añadir cancciones a la playlist
	@PostMapping("/addSongs")
	public ResponseEntity<?>saveSongsInPlaylist(@RequestBody @Valid SavePlaylistXSongDTO info, BindingResult validations){
		if(validations.hasErrors()) {
			
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), 
					HttpStatus.BAD_REQUEST);
			
		}
		try {
			System.out.println(info);
			
			//Verificando si el code de playlist existe
			UUID playlistCode = UUID.fromString(info.getPlaylist_code());
			if (playlistService.findOneById(playlistCode) == null) {
				return new ResponseEntity<>(
	                    new MessageDTO("Playlist not found"),
	                    HttpStatus.NOT_FOUND);
			}
			
			//Verificando que el code de song exista
			UUID songCode = UUID.fromString(info.getSong_code());
			System.out.println(songService.findOneById(songCode));
			if (songService.findOneById(songCode) == null) {
				return new ResponseEntity<>(
	                    new MessageDTO("Song not found"),
	                    HttpStatus.NOT_FOUND);
			}
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = format.parse(info.getDate_added());
			Timestamp timestamp = new Timestamp(date.getTime());
			//Si todo está correcto
			playlistXSongService.save(timestamp, playlistCode, songCode);
			return new ResponseEntity<>(
					new MessageDTO("Song added to playlist " + info), HttpStatus.CREATED);
			
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(
					new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/allPlaylist")
	public ResponseEntity<?>getAllPlaylist(){
		List<PlaylistXSong> playlists = playlistXSongService.findAll();
		return new ResponseEntity<>(playlists, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?>getById(@PathVariable(name = "id") String code){
		
		try {
			UUID playlistCode = UUID.fromString(code);
			PlaylistXSong playlist = playlistXSongService.findOneById(playlistCode);
			
			if (playlist == null) {
                return new ResponseEntity<>(
                        new MessageDTO("Playlist not found"),
                        HttpStatus.NOT_FOUND);
            }
            
            return new ResponseEntity<>(playlist, HttpStatus.OK);
		} catch (Exception e) {
			 return new ResponseEntity<>(
	                    new MessageDTO("Invalid playlist ID format"),
	                    HttpStatus.BAD_REQUEST);
		}
		
	}
	//Se busca eliminar la la canción añadida a la playlist
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteSongFromPlaylist(@PathVariable(name = "id") UUID id){
		try {
	        playlistXSongService.delete(id.toString());
	        return new ResponseEntity<>(
	                new MessageDTO("Song deleted from playlist "),
	                HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(
	                new MessageDTO("Internal Server Error"),
	                HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
}
