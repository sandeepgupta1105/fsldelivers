package com.newage.fsldelivers.repository;

import com.newage.fsldelivers.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);

	Boolean existsByCustomerId(String customerId);

	Optional<User> findByCustomerId(String customerId);
}
