package com.parcial2.models.entities;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "songPlaylist")

public class SongPlaylist {
	@Id
	@Column(name = "code")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID code;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "song_code", nullable = true)
	private Song songCode;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "playList_code", nullable = true)
	private Playlist playListCode;
	
	@Column(name = "date_added")
	private Date dateAdded;
	

	public SongPlaylist(Song songCode, Playlist playListCode, Date dateAdded) {
		super();
		this.songCode = songCode;
		this.playListCode = playListCode;
		this.dateAdded = dateAdded;
	}

}
