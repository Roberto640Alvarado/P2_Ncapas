package com.parcial2.services.implementations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.parcial2.models.dtos.MessageDTO;
import com.parcial2.models.dtos.SavePlaylistDTO;
import com.parcial2.models.entities.Playlist;
import com.parcial2.models.entities.User;
import com.parcial2.repositories.PlaylistRepository;
import com.parcial2.repositories.UserRepository;
import com.parcial2.services.PlaylistService;
import com.parcial2.services.UserService;

import jakarta.transaction.Transactional;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;
    


    @Override
    public void save(SavePlaylistDTO info, User user) throws Exception {
    	Playlist playlist = new Playlist(
    		    info.getTitle(),
    		    info.getDescription(),
    		    user
    		);
      // Verificar si el userCode es nulo o vacío
         String userCodeString = info.getUser_code();
         if (userCodeString == null || userCodeString.isEmpty()) {
        	 throw new Exception("Vacio");
         }
         
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

    @Override
    public void updatePlaylist(UUID playlistCode, String newTitle, String newDescription) throws Exception {
        Optional<Playlist> playlistOptional = playlistRepository.findById(playlistCode);
        if (playlistOptional.isPresent()) {
            Playlist playlist = playlistOptional.get();
            playlist.setTitle(newTitle);
            playlist.setDescription(newDescription);
            playlistRepository.save(playlist);
        } else {
            throw new Exception("Playlist not found");
        }
    }
	

}
