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
    date = "2024-11-05T19:46:08-0300",
    comments = "version: 1.6.0.Beta1, compiler: Eclipse JDT (IDE) 3.40.0.z20241023-1306, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toDomain(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UUID id = null;
        String username = null;
        String password = null;
        String name = null;
        String email = null;
        boolean active = false;
        UserRole role = null;
        Date createdAt = null;

        id = entity.getId();
        username = entity.getUsername();
        password = entity.getPassword();
        name = entity.getName();
        email = entity.getEmail();
        active = entity.isActive();
        role = entity.getRole();
        createdAt = entity.getCreatedAt();

        User user = new User( id, username, password, name, email, active, role, createdAt );

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

        userEntity.setUsername( domain.getUsername() );
        userEntity.setPassword( domain.getPassword() );
        userEntity.setName( domain.getName() );
        userEntity.setEmail( domain.getEmail() );
        userEntity.setActive( domain.isActive() );
        userEntity.setRole( domain.getRole() );

        return userEntity;
    }

    @Override
    public User toDomain(CreateUserDto request) {
        if ( request == null ) {
            return null;
        }

        String username = null;
        String password = null;
        String name = null;
        String email = null;

        username = request.username();
        password = request.password();
        name = request.name();
        email = request.email();

        UUID id = null;
        Date createdAt = null;
        boolean active = false;
        UserRole role = null;

        User user = new User( id, username, password, name, email, active, role, createdAt );

        return user;
    }
}
