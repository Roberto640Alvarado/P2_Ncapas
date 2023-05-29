package com.parcial2.repositories;

import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.parcial2.models.dtos.SavePlaylistXSongDTO;
import com.parcial2.models.entities.PlaylistXSong;

public interface PlaylistXSongRepository extends ListCrudRepository<PlaylistXSong, UUID>{
	PlaylistXSong save(SavePlaylistXSongDTO info);
}
