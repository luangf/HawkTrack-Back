package com.talkovia.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserRole {
    ADMIN("admin"),
    USER("user");

    @Getter
    private final String role;
}
