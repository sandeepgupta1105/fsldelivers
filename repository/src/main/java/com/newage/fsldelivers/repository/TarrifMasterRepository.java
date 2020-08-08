package com.newage.fsldelivers.repository;

import com.newage.fsldelivers.model.TarrifMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarrifMasterRepository extends JpaRepository<TarrifMaster, Long> {

    @Query(" FROM TarrifMaster tm ")
    List<TarrifMaster> findRatePerKgAll(@Param("wtInKg") double wtInKg);

    @Query("SELECT tm.ratePerKg FROM TarrifMaster tm WHERE tm.fromWt <= :wtInKg AND tm.toWt >= :wtInKg ")
    Double findRatePerKg(@Param("wtInKg") double wtInKg);
}
