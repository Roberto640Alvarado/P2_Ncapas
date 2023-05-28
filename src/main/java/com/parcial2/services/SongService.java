package com.parcial2.services;

import java.time.Duration;
import java.util.List;

import com.parcial2.models.dtos.SaveSongDTO;
import com.parcial2.models.entities.Song;

public interface SongService {
	void save(String title, Duration duration) throws Exception;
	void deleteById(String id) throws Exception;
	Song findOneById(String id); //Cuando queremos encontrar uno por id el tipo de función debe de ser del mismo tipo de lo que queremos buscar
	List<Song> findAll();
}
