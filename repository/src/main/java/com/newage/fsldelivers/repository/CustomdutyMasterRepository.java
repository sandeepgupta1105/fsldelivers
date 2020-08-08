package com.newage.fsldelivers.repository;

import com.newage.fsldelivers.model.CountryMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomdutyMasterRepository extends JpaRepository<CountryMaster, Long> {

    @Query("SELECT cdm.rateOfInterest FROM CustomdutyMaster cdm WHERE cdm.fromCost <= :totalCost AND cdm.toCost >= :totalCost ")
    Double findCustomDutyRate(@Param("totalCost") double totalCost);
}
