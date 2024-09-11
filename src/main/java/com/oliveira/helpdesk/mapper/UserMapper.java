package com.oliveira.helpdesk.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.oliveira.helpdesk.domain.User;
import com.oliveira.helpdesk.dto.CreateUserDto;
import com.oliveira.helpdesk.dto.UserDto;
import com.oliveira.helpdesk.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

  User toDomain(UserEntity entity);

  UserDto toDto(User damin);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "updateAt", ignore = true)
  @Mapping(target = "updatedBy", ignore = true)
  UserEntity toEntity(User domain);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "active", ignore = true)
  @Mapping(target = "role", ignore = true)
  User toDomain(CreateUserDto request);

}
