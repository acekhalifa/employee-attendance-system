package com.encentral.user.api;

import com.attendancemgmt.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUser {
    User save(User user);
    void deleteUser(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByToken(String token);
    List<User> findAll();
    boolean existsByEmail(String email);
}
