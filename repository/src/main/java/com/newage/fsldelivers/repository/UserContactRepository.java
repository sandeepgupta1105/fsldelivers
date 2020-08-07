package com.newage.fsldelivers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newage.fsldelivers.model.UserContact;

@Repository
public interface UserContactRepository extends JpaRepository<UserContact, Long>{

}