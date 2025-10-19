package com.encentral.scaffold.commons.util;

import com.attendancemgmt.entities.User;
import com.encentral.user.api.IUser;

import javax.inject.Inject;

public class TokenValidator {
    private final IUser userRepo;

    @Inject
    public TokenValidator(IUser userRepo) {
        this.userRepo = userRepo;
    }

    public User verifyAdmin(String token){
        var user = userRepo.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Token"));

        if(user.getRole() != (User.Role.ADMIN)){
            throw new SecurityException("Only Admins can perform this operation.");
        }
        return user;
    }

    public User verifyUser(String token){
        return userRepo.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Token"));

    }
}
