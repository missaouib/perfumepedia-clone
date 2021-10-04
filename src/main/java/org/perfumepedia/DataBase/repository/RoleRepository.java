package org.perfumepedia.DataBase.repository;

import org.perfumepedia.DataBase.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRole(String role);
}
