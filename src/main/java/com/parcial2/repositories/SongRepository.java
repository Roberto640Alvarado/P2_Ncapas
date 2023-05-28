package com.parcial2.repositories;

import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.parcial2.models.dtos.SaveSongDTO;
import com.parcial2.models.entities.Song;

public interface SongRepository extends ListCrudRepository<Song, UUID> {
	Song save(SaveSongDTO info);
}
