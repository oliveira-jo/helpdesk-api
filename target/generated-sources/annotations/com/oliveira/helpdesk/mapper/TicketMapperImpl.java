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
    date = "2024-09-11T17:59:38-0300",
    comments = "version: 1.6.0.Beta1, compiler: Eclipse JDT (IDE) 3.39.0.v20240820-0604, environment: Java 17.0.12 (Eclipse Adoptium)"
)
@Component
public class TicketMapperImpl implements TicketMapper {

    @Override
    public Ticket toDomain(TicketEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Ticket ticket = new Ticket();

        ticket.setCreatedAt( entity.getCreatedAt() );
        ticket.setCreatedBy( entity.getCreatedBy() );
        ticket.setDescription( entity.getDescription() );
        ticket.setId( entity.getId() );
        ticket.setStatus( entity.getStatus() );
        ticket.setSubject( entity.getSubject() );
        ticket.setSupportUser( userEntityToUser( entity.getSupportUser() ) );
        ticket.setUpdateAt( entity.getUpdateAt() );
        ticket.setUpdatedBy( entity.getUpdatedBy() );

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
        UUID updatedBy = null;
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

        ticketEntity.setCreatedAt( domain.getCreatedAt() );
        ticketEntity.setDescription( domain.getDescription() );
        ticketEntity.setId( domain.getId() );
        ticketEntity.setStatus( domain.getStatus() );
        ticketEntity.setSubject( domain.getSubject() );

        return ticketEntity;
    }

    @Override
    public Ticket toDomain(CreateTicketDto request) {
        if ( request == null ) {
            return null;
        }

        Ticket ticket = new Ticket();

        ticket.setAttachments( attachmentDtoListToAttachmentList( request.attachments() ) );
        ticket.setDescription( request.description() );
        ticket.setSubject( request.subject() );

        return ticket;
    }

    @Override
    public TicketInteraction toDomain(CreateTicketInteractionDto dto) {
        if ( dto == null ) {
            return null;
        }

        TicketInteraction ticketInteraction = new TicketInteraction();

        ticketInteraction.setAttachments( attachmentDtoListToAttachmentList( dto.attachments() ) );
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
            list.add( ticketInteractionToTicketInteractionDto( ticketInteraction ) );
        }

        return list;
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

        id = user.id();
        username = user.username();
        name = user.name();
        email = user.email();
        active = user.active();
        role = user.role();
        createdAt = user.createdAt();

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

    protected AttachmentDto attachmentToAttachmentDto(Attachment attachment) {
        if ( attachment == null ) {
            return null;
        }

        String filename = null;
        String content = null;

        filename = attachment.filename();
        content = attachment.content();

        AttachmentDto attachmentDto = new AttachmentDto( filename, content );

        return attachmentDto;
    }

    protected List<AttachmentDto> attachmentListToAttachmentDtoList(List<Attachment> list) {
        if ( list == null ) {
            return null;
        }

        List<AttachmentDto> list1 = new ArrayList<AttachmentDto>( list.size() );
        for ( Attachment attachment : list ) {
            list1.add( attachmentToAttachmentDto( attachment ) );
        }

        return list1;
    }

    protected TicketInteractionDto ticketInteractionToTicketInteractionDto(TicketInteraction ticketInteraction) {
        if ( ticketInteraction == null ) {
            return null;
        }

        UUID id = null;
        String message = null;
        TicketStatus status = null;
        List<AttachmentDto> attachments = null;
        UserDto sentByUser = null;
        Date updateAt = null;

        id = ticketInteraction.getId();
        message = ticketInteraction.getMessage();
        status = ticketInteraction.getStatus();
        attachments = attachmentListToAttachmentDtoList( ticketInteraction.getAttachments() );
        sentByUser = userEntityToUserDto( ticketInteraction.getSentByUser() );
        updateAt = ticketInteraction.getUpdateAt();

        TicketInteractionDto ticketInteractionDto = new TicketInteractionDto( id, message, status, attachments, sentByUser, updateAt );

        return ticketInteractionDto;
    }

    protected TicketInteraction ticketInteractionEntityToTicketInteraction(TicketInteractionEntity ticketInteractionEntity) {
        if ( ticketInteractionEntity == null ) {
            return null;
        }

        TicketInteraction ticketInteraction = new TicketInteraction();

        ticketInteraction.setCreatedAt( ticketInteractionEntity.getCreatedAt() );
        ticketInteraction.setCreatedBy( ticketInteractionEntity.getCreatedBy() );
        ticketInteraction.setId( ticketInteractionEntity.getId() );
        ticketInteraction.setMessage( ticketInteractionEntity.getMessage() );
        ticketInteraction.setSentByUser( ticketInteractionEntity.getSentByUser() );
        ticketInteraction.setStatus( ticketInteractionEntity.getStatus() );
        ticketInteraction.setTicket( ticketInteractionEntity.getTicket() );
        ticketInteraction.setUpdateAt( ticketInteractionEntity.getUpdateAt() );
        ticketInteraction.setUpdatedBy( ticketInteractionEntity.getUpdatedBy() );

        return ticketInteraction;
    }
}