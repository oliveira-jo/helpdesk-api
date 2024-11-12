package com.oliveira.helpdesk.domain;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.oliveira.helpdesk.entity.UserEntity;
import com.oliveira.helpdesk.enums.TicketStatus;

public class Ticket {

        private UUID id;
        private User supportUser;
        private List<Attachment> attachments;

        private String subject;
        private String description;
        private TicketStatus status;
        private UUID createdByUserId;

        private UserEntity createdBy;
        private Date createdAt;
        private UUID updatedBy;
        private Date updateAt;

        public UUID getId() {
                return id;
        }

        public void setId(UUID id) {
                this.id = id;
        }

        public User getSupportUser() {
                return supportUser;
        }

        public void setSupportUser(User supportUser) {
                this.supportUser = supportUser;
        }

        public List<Attachment> getAttachments() {
                return attachments;
        }

        public void setAttachments(List<Attachment> attachments) {
                this.attachments = attachments;
        }

        public String getSubject() {
                return subject;
        }

        public void setSubject(String subject) {
                this.subject = subject;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public TicketStatus getStatus() {
                return status;
        }

        public void setStatus(TicketStatus status) {
                this.status = status;
        }

        public UUID getCreatedByUserId() {
                return createdByUserId;
        }

        public void setCreatedByUserId(UUID createdByUserId) {
                this.createdByUserId = createdByUserId;
        }

        public UserEntity getCreatedBy() {
                return createdBy;
        }

        public void setCreatedBy(UserEntity createdBy) {
                this.createdBy = createdBy;
        }

        public Date getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(Date createdAt) {
                this.createdAt = createdAt;
        }

        public UUID getUpdatedBy() {
                return updatedBy;
        }

        public void setUpdatedBy(UUID updatedBy) {
                this.updatedBy = updatedBy;
        }

        public Date getUpdateAt() {
                return updateAt;
        }

        public void setUpdateAt(Date updateAt) {
                this.updateAt = updateAt;
        }

}