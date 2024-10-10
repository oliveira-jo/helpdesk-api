package com.oliveira.helpdesk.configuration;

import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.oliveira.helpdesk.entity.UserEntity;
import com.oliveira.helpdesk.enums.UserRole;
import com.oliveira.helpdesk.repository.UserRepository;

@Component
public class Config {

  UserRepository repository;

  Config(UserRepository repository) {
    this.repository = repository;
    this.veryfyAdminBd();
  }

  public void veryfyAdminBd() {

    var userAdmin = this.repository.findByUsername("admin");
    if (userAdmin.isPresent()) {
      System.out.println("------------------------- Administrador already saved in BD -------------------------");

    } else {

      UserEntity entity = new UserEntity();
      entity.setCreatedAt(new Date());
      entity.setUpdateAt(new Date());
      entity.setPassword(new BCryptPasswordEncoder().encode("password"));
      entity.setActive(true);
      entity.setRole(UserRole.ADMIN);
      entity.setEmail("admin@");
      entity.setName("administrador");
      entity.setUsername("admin");

      this.repository.save(entity);
      var adminUser = this.repository.findByUsername("admin");
      System.out.println("------------------------- Administrador CREATE AND SAVE in BD ------------------------- "
          + adminUser.isPresent());

    }

  }

}
