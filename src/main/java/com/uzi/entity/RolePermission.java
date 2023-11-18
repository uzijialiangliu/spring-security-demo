package com.uzi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "role_permission")
public class RolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roleId;

    private Long permissionId;
}
