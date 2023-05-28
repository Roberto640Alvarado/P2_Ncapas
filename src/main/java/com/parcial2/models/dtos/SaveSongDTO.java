package com.parcial2.models.dtos;

import java.time.Duration;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SaveSongDTO {
	@NotEmpty(message = "The title song is required!")
	private String title;
	@NotEmpty(message = "The duration song is required!")
	private String duration;
}
