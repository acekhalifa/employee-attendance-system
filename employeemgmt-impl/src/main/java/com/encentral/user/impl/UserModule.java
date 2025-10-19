package com.encentral.user.impl;

import com.encentral.user.api.IUser;
import com.encentral.user.api.IUserService;
import com.google.inject.AbstractModule;

public class UserModule extends AbstractModule {
    @Override
    protected void configure(){
        bind(IUser.class).to(DefaultUserImpl.class);
        bind(IUserService.class).to(UserServiceImpl.class);
    }
}
