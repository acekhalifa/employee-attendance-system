package com.encentral.user.model;

import com.attendancemgmt.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "jsr330")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    @Mapping(source = "role", target = "role", qualifiedByName = "roleToString")
    UserResponse toDTO(User user);

    User toEntity(UserRequest request);

    List<UserResponse> toDTOList(List<User> users);

    @org.mapstruct.Named("roleToString")
    default String roleToString(User.Role role) {
        return role != null ? role.name() : null;
    }
}
