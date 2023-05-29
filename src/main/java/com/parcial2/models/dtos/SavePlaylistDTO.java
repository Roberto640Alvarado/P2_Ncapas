package com.parcial2.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SavePlaylistDTO {
    @NotEmpty(message = "Name is required")
    private String title;
    
    @NotEmpty(message = "Description is required")
    private String description;
    
    @NotEmpty(message = "User Code is required")
    private String user_code;
}

