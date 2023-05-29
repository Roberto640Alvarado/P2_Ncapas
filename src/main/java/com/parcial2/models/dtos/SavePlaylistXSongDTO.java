package com.parcial2.models.dtos;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SavePlaylistXSongDTO {
	//@NotEmpty(message = "The date is required!")
	private String date_added;
	@NotEmpty(message = "Playlist code is required!")
	private String playlist_code;
	@NotEmpty(message = "Song code is required!")
	//private List<String> song_code;
	private String song_code;
	/*
	public String getSong_code() {
        return song_code;
    }

    public void setSong_code(String song_code) {
        this.song_code = song_code;
    }
    
    public String getPlaylist_code() {
        return playlist_code;
    }

    public void setPlaylist_code(String playlist_code) {
        this.playlist_code = playlist_code;
    }
    */
}
