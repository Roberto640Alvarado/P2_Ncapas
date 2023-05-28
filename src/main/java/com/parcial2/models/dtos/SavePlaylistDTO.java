package com.parcial2.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SavePlaylistDTO {
    @NotEmpty(message = "Name is required")
    private String title;
    
    @NotEmpty(message = "Description is required")
    private String description;
    
    private String user_code;

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }
}

