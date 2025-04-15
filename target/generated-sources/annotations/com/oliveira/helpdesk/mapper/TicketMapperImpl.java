package com.oliveira.helpdesk.mapper;

import com.oliveira.helpdesk.domain.Attachment;
import com.oliveira.helpdesk.domain.Ticket;
import com.oliveira.helpdesk.domain.TicketInteraction;
import com.oliveira.helpdesk.domain.User;
import com.oliveira.helpdesk.dto.AttachmentDto;
import com.oliveira.helpdesk.dto.CreateTicketDto;
import com.oliveira.helpdesk.dto.CreateTicketInteractionDto;
import com.oliveira.helpdesk.dto.TicketDto;
import com.oliveira.helpdesk.dto.TicketInteractionDto;
import com.oliveira.helpdesk.dto.UserDto;
import com.oliveira.helpdesk.entity.TicketEntity;
import com.oliveira.helpdesk.entity.TicketInteractionEntity;
import com.oliveira.helpdesk.entity.UserEntity;
import com.oliveira.helpdesk.enums.TicketStatus;
import com.oliveira.helpdesk.enums.UserRole;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-15T14:35:20-0300",
    comments = "version: 1.6.0.Beta1, compiler: Eclipse JDT (IDE) 3.42.0.z20250331-1358, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class TicketMapperImpl implements TicketMapper {

    @Override
    public Ticket toDomain(TicketEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Ticket ticket = new Ticket();

        ticket.setId( entity.getId() );
        ticket.setSupportUser( userEntityToUser( entity.getSupportUser() ) );
        ticket.setSubject( entity.getSubject() );
        ticket.setDescription( entity.getDescription() );
        ticket.setStatus( entity.getStatus() );
        ticket.setCreatedBy( entity.getCreatedBy() );
        ticket.setCreatedAt( entity.getCreatedAt() );
        ticket.setUpdatedBy( entity.getUpdatedBy() );
        ticket.setUpdateAt( entity.getUpdateAt() );

        return ticket;
    }

    @Override
    public TicketDto toDto(Ticket damin) {
        if ( damin == null ) {
            return null;
        }

        UUID id = null;
        UserDto supportUser = null;
        String subject = null;
        String description = null;
        TicketStatus status = null;
        UserDto createdBy = null;
        Date createdAt = null;
        UserEntity updatedBy = null;
        Date updateAt = null;

        id = damin.getId();
        supportUser = userToUserDto( damin.getSupportUser() );
        subject = damin.getSubject();
        description = damin.getDescription();
        status = damin.getStatus();
        createdBy = userEntityToUserDto( damin.getCreatedBy() );
        createdAt = damin.getCreatedAt();
        updatedBy = damin.getUpdatedBy();
        updateAt = damin.getUpdateAt();

        TicketDto ticketDto = new TicketDto( id, supportUser, subject, description, status, createdBy, createdAt, updatedBy, updateAt );

        return ticketDto;
    }

    @Override
    public TicketEntity toEntity(Ticket domain) {
        if ( domain == null ) {
            return null;
        }

        TicketEntity ticketEntity = new TicketEntity();

        ticketEntity.setId( domain.getId() );
        ticketEntity.setSubject( domain.getSubject() );
        ticketEntity.setDescription( domain.getDescription() );
        ticketEntity.setStatus( domain.getStatus() );
        ticketEntity.setCreatedAt( domain.getCreatedAt() );

        return ticketEntity;
    }

    @Override
    public Ticket toDomain(CreateTicketDto request) {
        if ( request == null ) {
            return null;
        }

        Ticket ticket = new Ticket();

        ticket.setAttachments( attachmentDtoListToAttachmentList( request.attachments() ) );
        ticket.setSubject( request.subject() );
        ticket.setDescription( request.description() );

        return ticket;
    }

    @Override
    public TicketInteraction toDomain(CreateTicketInteractionDto dto) {
        if ( dto == null ) {
            return null;
        }

        TicketInteraction ticketInteraction = new TicketInteraction();

        ticketInteraction.setMessage( dto.message() );

        return ticketInteraction;
    }

    @Override
    public List<Ticket> toDomain(List<TicketEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<Ticket> list = new ArrayList<Ticket>( entities.size() );
        for ( TicketEntity ticketEntity : entities ) {
            list.add( toDomain( ticketEntity ) );
        }

        return list;
    }

    @Override
    public List<TicketDto> toDto(List<Ticket> domains) {
        if ( domains == null ) {
            return null;
        }

        List<TicketDto> list = new ArrayList<TicketDto>( domains.size() );
        for ( Ticket ticket : domains ) {
            list.add( toDto( ticket ) );
        }

        return list;
    }

    @Override
    public List<TicketInteractionDto> toTicketInteractionsDto(List<TicketInteraction> domains) {
        if ( domains == null ) {
            return null;
        }

        List<TicketInteractionDto> list = new ArrayList<TicketInteractionDto>( domains.size() );
        for ( TicketInteraction ticketInteraction : domains ) {
            list.add( toTicketInteractionDto( ticketInteraction ) );
        }

        return list;
    }

    @Override
    public TicketInteractionDto toTicketInteractionDto(TicketInteraction domain) {
        if ( domain == null ) {
            return null;
        }

        UUID id = null;
        String message = null;
        TicketStatus status = null;
        UserDto sentByUser = null;
        Date createdAt = null;

        id = domain.getId();
        message = domain.getMessage();
        status = domain.getStatus();
        sentByUser = userEntityToUserDto( domain.getSentByUser() );
        createdAt = domain.getCreatedAt();

        List<AttachmentDto> attachments = null;

        TicketInteractionDto ticketInteractionDto = new TicketInteractionDto( id, message, status, attachments, sentByUser, createdAt );

        return ticketInteractionDto;
    }

    @Override
    public List<TicketInteraction> toInteractionsDomain(List<TicketInteractionEntity> byTicket) {
        if ( byTicket == null ) {
            return null;
        }

        List<TicketInteraction> list = new ArrayList<TicketInteraction>( byTicket.size() );
        for ( TicketInteractionEntity ticketInteractionEntity : byTicket ) {
            list.add( ticketInteractionEntityToTicketInteraction( ticketInteractionEntity ) );
        }

        return list;
    }

    protected User userEntityToUser(UserEntity userEntity) {
        if ( userEntity == null ) {
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

        id = userEntity.getId();
        username = userEntity.getUsername();
        password = userEntity.getPassword();
        name = userEntity.getName();
        email = userEntity.getEmail();
        active = userEntity.isActive();
        role = userEntity.getRole();
        createdAt = userEntity.getCreatedAt();

        User user = new User( id, username, password, name, email, active, role, createdAt );

        return user;
    }

    protected UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UUID id = null;
        String username = null;
        String name = null;
        String email = null;
        boolean active = false;
        UserRole role = null;
        Date createdAt = null;

        id = user.getId();
        username = user.getUsername();
        name = user.getName();
        email = user.getEmail();
        active = user.isActive();
        role = user.getRole();
        createdAt = user.getCreatedAt();

        UserDto userDto = new UserDto( id, username, name, email, active, role, createdAt );

        return userDto;
    }

    protected UserDto userEntityToUserDto(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UUID id = null;
        String username = null;
        String name = null;
        String email = null;
        boolean active = false;
        UserRole role = null;
        Date createdAt = null;

        id = userEntity.getId();
        username = userEntity.getUsername();
        name = userEntity.getName();
        email = userEntity.getEmail();
        active = userEntity.isActive();
        role = userEntity.getRole();
        createdAt = userEntity.getCreatedAt();

        UserDto userDto = new UserDto( id, username, name, email, active, role, createdAt );

        return userDto;
    }

    protected Attachment attachmentDtoToAttachment(AttachmentDto attachmentDto) {
        if ( attachmentDto == null ) {
            return null;
        }

        String filename = null;
        String content = null;

        filename = attachmentDto.filename();
        content = attachmentDto.content();

        Attachment attachment = new Attachment( filename, content );

        return attachment;
    }

    protected List<Attachment> attachmentDtoListToAttachmentList(List<AttachmentDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Attachment> list1 = new ArrayList<Attachment>( list.size() );
        for ( AttachmentDto attachmentDto : list ) {
            list1.add( attachmentDtoToAttachment( attachmentDto ) );
        }

        return list1;
    }

    protected TicketInteraction ticketInteractionEntityToTicketInteraction(TicketInteractionEntity ticketInteractionEntity) {
        if ( ticketInteractionEntity == null ) {
            return null;
        }

        TicketInteraction ticketInteraction = new TicketInteraction();

        ticketInteraction.setId( ticketInteractionEntity.getId() );
        ticketInteraction.setTicket( ticketInteractionEntity.getTicket() );
        ticketInteraction.setStatus( ticketInteractionEntity.getStatus() );
        ticketInteraction.setSentByUser( ticketInteractionEntity.getSentByUser() );
        ticketInteraction.setMessage( ticketInteractionEntity.getMessage() );
        ticketInteraction.setCreatedBy( ticketInteractionEntity.getCreatedBy() );
        ticketInteraction.setCreatedAt( ticketInteractionEntity.getCreatedAt() );
        ticketInteraction.setUpdatedBy( ticketInteractionEntity.getUpdatedBy() );
        ticketInteraction.setUpdateAt( ticketInteractionEntity.getUpdateAt() );

        return ticketInteraction;
    }
}
