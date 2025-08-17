package com.norhan.demo.mappers;

import com.norhan.demo.dtos.UserDto;
import com.norhan.demo.entities.User;
import com.norhan.demo.user.RegisterUserRequest;
import com.norhan.demo.user.UpdateUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
@Mapper(componentModel = "spring")
public interface UserMapper {

        UserDto toDto(User user);
        User toEntity(RegisterUserRequest request);

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "password", ignore = true)
        @Mapping(target = "role", ignore = true)
          void updateUser(UpdateUserRequest request, @MappingTarget User user);
}
