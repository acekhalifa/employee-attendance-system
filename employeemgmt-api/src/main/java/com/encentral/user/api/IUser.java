package com.encentral.user.api;

import com.attendancemgmt.entities.User;

import java.util.Optional;

public interface IUser {
    Optional<User> findByEmail(String email);
    Optional<User> findByToken(String token);
    boolean existsByEmail(String email);
}
