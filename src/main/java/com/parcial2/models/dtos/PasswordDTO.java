package com.parcial2.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PasswordDTO {
	@NotEmpty(message = "The title song is required!")
	private String oldPassword;
	@NotEmpty(message = "The duration song is required!")
	private String newPassword;

}
