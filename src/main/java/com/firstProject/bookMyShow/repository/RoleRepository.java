package com.firstProject.bookMyShow.repository;

import com.firstProject.bookMyShow.entities.Role;
import com.firstProject.bookMyShow.entities.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

   Optional<Role> findByName(RoleEnum name);
}
