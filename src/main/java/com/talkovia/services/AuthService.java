package com.talkovia.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.talkovia.customexceptions.InvalidCredentialsException;
import com.talkovia.customexceptions.ObjectNotFoundException;
import com.talkovia.customexceptions.UserAlreadyExistsException;
import com.talkovia.dto.auth.LoginRegisterResponseDTO;
import com.talkovia.dto.auth.LoginRequestDTO;
import com.talkovia.dto.auth.RegisterRequestDTO;
import com.talkovia.model.User;
import com.talkovia.repositories.UserRepository;
import com.talkovia.security.TokenService;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final CookieService cookieService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService, CookieService cookieService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.cookieService = cookieService;
        this.authenticationManager = authenticationManager;
    }

    public LoginRegisterResponseDTO login(LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        User user = (User) auth.getPrincipal();
        var token = tokenService.generateToken(user);
        cookieService.generateCookieWithJWT(token, response);
        return new LoginRegisterResponseDTO(user.getUsername());
    }

    public LoginRegisterResponseDTO register(RegisterRequestDTO registerRequestDTO, HttpServletResponse response) {
        UserDetails userByEmail = userRepository.findByEmail(registerRequestDTO.email());
        Optional<User> userByUsername = userRepository.findByUsername(registerRequestDTO.username());

        if(userByEmail != null && userByUsername.isPresent()){
            throw new UserAlreadyExistsException("Email and username already in use");
        }
        else if (userByEmail != null) {
            throw new UserAlreadyExistsException("Email already in use");
        }
        else if (userByUsername.isPresent()) {
            throw new UserAlreadyExistsException("Username already in use");
        }

        User newUser = new User(registerRequestDTO.email(), registerRequestDTO.username(), passwordEncoder.encode(registerRequestDTO.password()));
        userRepository.save(newUser);
        var token = tokenService.generateToken(newUser);
        cookieService.generateCookieWithJWT(token, response);
        return new LoginRegisterResponseDTO(newUser.getUsername());
    }

    public void logout(HttpServletResponse response) {
        cookieService.expirateCookie(response);
    }
}
