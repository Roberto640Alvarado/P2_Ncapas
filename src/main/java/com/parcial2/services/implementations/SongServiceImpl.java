package com.parcial2.services.implementations;

import java.time.Duration;
import java.util.List;

import org.postgresql.util.PGInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parcial2.models.dtos.SaveSongDTO;
import com.parcial2.models.entities.Song;
import com.parcial2.repositories.SongRepository;
import com.parcial2.services.SongService;

import jakarta.transaction.Transactional;

@Service
public class SongServiceImpl implements SongService {

	@Autowired
	private SongRepository songRepository;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void save(String title, String duration) throws Exception {
		Song newSong = new Song(
				title,
				duration
			);
	songRepository.save(newSong);
		
	}

	@Override
	public void deleteById(String id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Song findOneById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Song> findAll() {
		// TODO Auto-generated method stub
		return songRepository.findAll();
	}
	
}
