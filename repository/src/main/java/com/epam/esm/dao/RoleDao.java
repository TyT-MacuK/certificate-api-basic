package com.epam.esm.dao;

import com.epam.esm.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleDao extends JpaRepository<Role, Long> {//TODO check
    @Query(value = "SELECT id, role_name FROM role WHERE role_name = :name", nativeQuery = true)
    Role findByName(@Param("name") String name);
}
