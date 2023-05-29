package com.parcial2.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parcial2.models.dtos.SavePlaylistDTO;
import com.parcial2.models.entities.Playlist;
import com.parcial2.models.entities.User;
import com.parcial2.repositories.PlaylistRepository;
import com.parcial2.services.PlaylistService;
import com.parcial2.services.UserService;

import jakarta.transaction.Transactional;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private UserService userService;

    @Override
    public void save(SavePlaylistDTO playlistDTO) throws Exception {
    Playlist playlist = new Playlist();
    playlist.setTitle(playlistDTO.getTitle());
    playlist.setDescription(playlistDTO.getDescription()); // Agrega esta línea para establecer la descripción

    UUID userCode = UUID.fromString(playlistDTO.getUser_code());
    User user = userService.findOneById(userCode);
    if (user == null) {
        throw new Exception("User not found");
    }
    playlist.setUser_code(user);

    playlistRepository.save(playlist);
}


    @Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteById(String id) throws Exception {
	    try {
	        UUID playlistId = UUID.fromString(id);
	        playlistRepository.deleteById(playlistId);
	    } catch (IllegalArgumentException e) {
	        // Si el ID no es válido
	        throw new Exception("Invalid song ID");
	    } catch (Exception e) {
	        // Manejar otras excepciones
	        throw new Exception("Failed to delete user");
	    }
	}


    @Override
    public Playlist findOneById(UUID id) {
        return playlistRepository.findById(id).orElse(null);
    }

    @Override
    public List<Playlist> findAll() {
        return playlistRepository.findAll();
    }
}
