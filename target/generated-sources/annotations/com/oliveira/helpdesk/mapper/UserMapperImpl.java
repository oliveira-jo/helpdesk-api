package com.oliveira.helpdesk.mapper;

import com.oliveira.helpdesk.domain.User;
import com.oliveira.helpdesk.dto.CreateUserDto;
import com.oliveira.helpdesk.dto.UserDto;
import com.oliveira.helpdesk.entity.UserEntity;
import com.oliveira.helpdesk.enums.UserRole;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-16T15:34:50-0300",
    comments = "version: 1.6.0.Beta1, compiler: Eclipse JDT (IDE) 3.42.0.z20250331-1358, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toDomain(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        User user = new User();

        user.setActive( entity.isActive() );
        user.setCreatedAt( entity.getCreatedAt() );
        user.setEmail( entity.getEmail() );
        user.setId( entity.getId() );
        user.setName( entity.getName() );
        user.setPassword( entity.getPassword() );
        user.setRole( entity.getRole() );
        user.setUsername( entity.getUsername() );

        return user;
    }

    @Override
    public UserDto toDto(User damin) {
        if ( damin == null ) {
            return null;
        }

        UUID id = null;
        String username = null;
        String name = null;
        String email = null;
        boolean active = false;
        UserRole role = null;
        Date createdAt = null;

        id = damin.getId();
        username = damin.getUsername();
        name = damin.getName();
        email = damin.getEmail();
        active = damin.isActive();
        role = damin.getRole();
        createdAt = damin.getCreatedAt();

        UserDto userDto = new UserDto( id, username, name, email, active, role, createdAt );

        return userDto;
    }

    @Override
    public List<UserDto> toDto(List<UserEntity> damin) {
        if ( damin == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( damin.size() );
        for ( UserEntity userEntity : damin ) {
            list.add( toDto( toDomain( userEntity ) ) );
        }

        return list;
    }

    @Override
    public UserEntity toEntity(User domain) {
        if ( domain == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setActive( domain.isActive() );
        userEntity.setEmail( domain.getEmail() );
        userEntity.setName( domain.getName() );
        userEntity.setPassword( domain.getPassword() );
        userEntity.setRole( domain.getRole() );
        userEntity.setUsername( domain.getUsername() );

        return userEntity;
    }

    @Override
    public User toDomain(CreateUserDto request) {
        if ( request == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( request.email() );
        user.setName( request.name() );
        user.setPassword( request.password() );
        user.setUsername( request.username() );

        return user;
    }
}
