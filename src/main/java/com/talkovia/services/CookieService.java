package com.talkovia.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class CookieService {
    private final String AUTH_TOKEN_COOKIE_NAME = "auth-token";

    public void generateCookieWithJWT(String token, HttpServletResponse response){
        Cookie cookie = new Cookie(AUTH_TOKEN_COOKIE_NAME, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // colocar true em prod; Apenas para HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(7200); //2h
        response.addCookie(cookie);
    }

    public void expirateCookie(HttpServletResponse response){
        Cookie cookie = new Cookie(AUTH_TOKEN_COOKIE_NAME, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // colocar true em prod; Apenas para HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(0); //expira ele
        response.addCookie(cookie);
    }
}
