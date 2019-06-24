package com.roles.ai.demo.Repository;


import com.roles.ai.demo.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
