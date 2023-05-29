package com.parcial2.services.implementations;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parcial2.models.dtos.SavePlaylistXSongDTO;
import com.parcial2.models.entities.Playlist;
import com.parcial2.models.entities.PlaylistXSong;
import com.parcial2.models.entities.Song;
import com.parcial2.repositories.PlaylistXSongRepository;
import com.parcial2.services.PlaylistService;
import com.parcial2.services.PlaylistXSongService;
import com.parcial2.services.SongService;

import jakarta.transaction.Transactional;

@Service
public class PlaylistXSongServiceImpl implements PlaylistXSongService {

	

	@Autowired
	private PlaylistXSongRepository playlistXSongRepository;
	
	@Autowired
	private SongService songService;
	
	@Autowired
	private PlaylistService playlistService;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void save(Timestamp date_added, UUID playlist_code, UUID song_code) throws Exception {
		// TODO Auto-generated method stub
		PlaylistXSong playlistSong = new PlaylistXSong();
		//Convirtiendo a tipo UUID
		//UUID playlistCode = UUID.fromString(info.getPlaylist_code());
		//UUID songCode = UUID.fromString(info.getSong_code());
		//Buscando el id
		Song song = songService.findOneById(song_code);
		Playlist playlist = playlistService.findOneById(playlist_code);
		playlistSong.setDateAdded(date_added);
		playlistSong.setSong_code(song);
		playlistSong.setPlaylist_code(playlist);
		
		/*playlistXSongRepository.save(info);*/
		playlistXSongRepository.save(playlistSong);
		
	}

	@Override
	public List<PlaylistXSong> findAll() {
		// TODO Auto-generated method stub
		return playlistXSongRepository.findAll();
	}
	
	@Override
	public PlaylistXSong findOneById(UUID id) {
		// TODO Auto-generated method stub
		return playlistXSongRepository.findById(id).orElse(null);
	}
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void delete(String insertion_code) throws Exception {
		// TODO Auto-generated method stub
		try {
			UUID insertionId = UUID.fromString(insertion_code);
			playlistXSongRepository.deleteById(insertionId);
		} catch (IllegalArgumentException e) {
			throw new Exception("Elimination faild");
		}
	}

	
}
