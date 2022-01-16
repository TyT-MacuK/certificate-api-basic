package com.epam.esm.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Data
public class Role extends AbstractEntity {
@Enumerated(EnumType.STRING)
@Column(name = "role_name")
   private User.UserRole role;
}
