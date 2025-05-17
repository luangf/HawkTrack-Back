package com.talkovia.repositories;

import com.talkovia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	UserDetails findByEmail(String email);
	UserDetails findByUsername(String username);
}
