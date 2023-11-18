package com.uzi.repository;

import com.uzi.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    String QUERY_SQL = """
               SELECT p.*
               FROM USER u
               LEFT JOIN user_role ur ON u.id = ur.user_id
               LEFT JOIN role r ON r.id = ur.role_id
               LEFT JOIN role_permission rp ON r.id = rp.role_id
               LEFT JOIN permission p ON p.id = rp.permission_id
               WHERE u.id = ?1
            """;

    @Query(value = QUERY_SQL, nativeQuery = true)
    List<Permission> selectByUserId(Long userId);
}
