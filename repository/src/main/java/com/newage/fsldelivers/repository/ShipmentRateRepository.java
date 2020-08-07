package com.newage.fsldelivers.repository;

import com.newage.fsldelivers.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ShipmentRateRepository extends JpaRepository<User, Long> {

	@Query(nativeQuery = true, value = "SELECT pk_currency_rate_master.get_currency_rate(:text, :text1, :amount, :date) FROM dual")
	double getExchangeRate(@Param("text") String text, @Param("text1") String text1, @Param("amount") double amount, @Param("date") Date date);
}
