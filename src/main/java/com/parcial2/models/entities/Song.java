package com.parcial2.models.entities;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Type;
import org.hibernate.dialect.PostgreSQLCastingIntervalSecondJdbcType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "song")
public class Song {
	@Id
	@Column(name = "code")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private UUID code;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "duration")//La pase a String porque no sé como convertir un duration a interval
	private String duration;
	
	//@OneToMany(mappedBy = "song", fetch = FetchType.LAZY)
	//@JsonIgnore
	//private List<Song> songs;

	public Song(String title,String duration) {
		super();
		this.title = title;
		this.duration = duration;
		
	}

}