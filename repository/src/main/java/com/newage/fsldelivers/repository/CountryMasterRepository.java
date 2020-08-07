package com.newage.fsldelivers.repository;

import com.newage.fsldelivers.model.CountryMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Date;

@Repository
public interface CountryMasterRepository extends JpaRepository<CountryMaster, Long> {

    @Query("select cm.currencyCode FROM CountryMaster cm WHERE UPPER(cm.countryCode) = :countryCode")
    String findByCountryCode(@Param("countryCode") String countryCode);
}
