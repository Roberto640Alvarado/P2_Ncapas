package com.parcial2.services;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import org.postgresql.util.PGInterval;

import com.parcial2.models.dtos.SaveSongDTO;
import com.parcial2.models.entities.Song;

public interface SongService {
	void save(String title, String duration) throws Exception;
	void deleteById(String id) throws Exception;
	Song findOneById(UUID id); //Cuando queremos encontrar uno por id el tipo de función debe de ser del mismo tipo de lo que queremos buscar
	List<Song> findAll();
	void update(UUID id, SaveSongDTO updatedSong) throws Exception;
}
