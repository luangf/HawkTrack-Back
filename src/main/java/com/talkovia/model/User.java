package com.talkovia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false, length = 254)
	private String email;
	
	@Column(unique = true, nullable = false, length = 30)
	private String username;
	
	@Column(nullable = false, length = 60)
	private String password;
	
	//@Column(nullable = true)
	//private LocalDateTime lastLogin;
	
	//@Column(nullable = false)
	//private boolean active = true;
}
