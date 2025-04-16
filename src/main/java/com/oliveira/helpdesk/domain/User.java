package com.oliveira.helpdesk.domain;

import java.util.Date;
import java.util.UUID;

import com.oliveira.helpdesk.enums.UserRole;

public class User {

        private UUID id;
        private String username;
        private String password;
        private String name;
        private String email;
        private boolean active;
        private UserRole role;
        private Date createdAt;

        public User() {
        }

        public User(UUID id, String username, String password, String name, String email, boolean active, UserRole role,
                        Date createdAt) {
                this.id = id;
                this.username = username;
                this.password = password;
                this.name = name;
                this.email = email;
                this.active = active;
                this.role = role;
                this.createdAt = createdAt;
        }

        public UUID getId() {
                return id;
        }

        public void setId(UUID id) {
                this.id = id;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public boolean isActive() {
                return active;
        }

        public void setActive(boolean active) {
                this.active = active;
        }

        public UserRole getRole() {
                return role;
        }

        public void setRole(UserRole role) {
                this.role = role;
        }

        public Date getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(Date createdAt) {
                this.createdAt = createdAt;
        }
}