package com.vira.vpm.authservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "auth_user")
@Builder
@Data
public class AuthUser {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "id")
  private String id;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;
}
