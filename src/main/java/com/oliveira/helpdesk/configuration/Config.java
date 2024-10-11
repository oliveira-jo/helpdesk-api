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

    // CONFIG ABOUT ADMINISTRATOR
    var userAdmin = this.repository.findByUsername("admin");
    if (!userAdmin.isPresent()) {
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
      userAdmin = this.repository.findByUsername("admin");
      System.out.println("------------------------- ADMINISTRATOR CREATE AND SAVE IN BD ------------------------- "
          + userAdmin.isPresent());

    } else {
      System.out.println("------------------------- ADMINISTRATOR ALREADY EXIST IN BD -------------------------");

    }

    // CONFIG ABOUT ADMINISTRATOR
    var userSuport = this.repository.findByUsername("suport");
    if (!userSuport.isPresent()) {
      UserEntity entity = new UserEntity();
      entity.setUpdateAt(new Date());
      entity.setCreatedAt(new Date());
      entity.setPassword(new BCryptPasswordEncoder().encode("password"));
      entity.setActive(true);
      entity.setRole(UserRole.SUPPORT_ATTENDANT);
      entity.setEmail("suport@");
      entity.setName("Suport Attendent");
      entity.setUsername("suport");

      this.repository.save(entity);
      userSuport = this.repository.findByUsername("admin");
      System.out.println("------------------------- SUPORT USER CREATE AND SAVE IN BD ------------------------- "
          + userSuport.isPresent());

    } else {
      System.out.println("------------------------- SUPORT USER ALREADY EXIST IN BD -------------------------");

    }

  }

}
