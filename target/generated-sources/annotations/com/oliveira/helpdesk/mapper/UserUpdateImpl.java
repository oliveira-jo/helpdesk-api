package com.oliveira.helpdesk.mapper;

import com.oliveira.helpdesk.dto.CreateUserDto;
import com.oliveira.helpdesk.entity.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-11T17:59:37-0300",
    comments = "version: 1.6.0.Beta1, compiler: Eclipse JDT (IDE) 3.39.0.v20240820-0604, environment: Java 17.0.12 (Eclipse Adoptium)"
)
@Component
public class UserUpdateImpl implements UserUpdate {

    @Override
    public void updateUser(CreateUserDto dto, UserEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.email() != null ) {
            entity.setEmail( dto.email() );
        }
        if ( dto.name() != null ) {
            entity.setName( dto.name() );
        }
        if ( dto.password() != null ) {
            entity.setPassword( dto.password() );
        }
        if ( dto.username() != null ) {
            entity.setUsername( dto.username() );
        }
    }
}
