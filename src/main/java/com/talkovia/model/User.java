package com.talkovia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Set;

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

	@CreationTimestamp
	private Instant createdAt;

	@UpdateTimestamp
	private Instant updatedAt;

	private Instant lastLoginAt;

	@Column(nullable = false)
	private boolean active = true;
	
	@Column(nullable = false)
	private boolean emailVerified = false;
	
	//maybe convert to S3 AWS imagePath
	//private String imagePath;
	
	@Lob
    private byte[] image;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Role> roles;
}
