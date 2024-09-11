package com.oliveira.helpdesk.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.oliveira.helpdesk.dto.CreateUserDto;
import com.oliveira.helpdesk.entity.UserEntity;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserUpdate {

  void updateUser(CreateUserDto dto, @MappingTarget UserEntity entity);

}
