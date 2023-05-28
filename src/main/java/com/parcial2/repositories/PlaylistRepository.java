package com.parcial2.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parcial2.models.entities.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, UUID> {
}
