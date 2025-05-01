package com.talkovia.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
//@EqualsAndHashCode(of="id")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false, length = 254)
	private String email;

	@Column(unique = true, nullable = false, length = 30)
	private String username;

	@Column(nullable = false, length = 60)
	private String password;

	//maybe change to UTC future, timezone padrao banco...
	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	private LocalDateTime lastLoginAt;

	@Column(nullable = false)
	private boolean active = true;
	
	@Column(nullable = false)
	private boolean emailVerified = false;
	
	//maybe convert to S3 AWS imagePath
	//private String imagePath;
	
	@Lob
    private byte[] image;
}
