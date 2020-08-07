package com.newage.fsldelivers.repository;

import com.newage.fsldelivers.enums.ERole;
import com.newage.fsldelivers.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
