package com.newage.fsldelivers.service;

import com.newage.fsldelivers.model.TarrifMaster;
import com.newage.fsldelivers.repository.CountryMasterRepository;
import com.newage.fsldelivers.repository.CustomdutyMasterRepository;
import com.newage.fsldelivers.repository.ShipmentRateRepository;
import com.newage.fsldelivers.repository.TarrifMasterRepository;
import com.newage.fsldelivers.service.exception.ServiceErrorCode;
import com.newage.fsldelivers.service.exception.ServiceErrorMessage;
import com.newage.fsldelivers.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ShipmentRateService {

    @Autowired
    ShipmentRateRepository shipmentRateRepository;

    @Autowired
    CountryMasterRepository countryMasterRepository;

    @Autowired
    TarrifMasterRepository tarrifMasterRepository;

    @Autowired
    CustomdutyMasterRepository customdutyMasterRepository;

    private static final Logger logger = LogManager.getLogger(ShipmentRateService.class);

    public Double getShipmentRate(String destCountryCode, double weightInKg, double WeightInLbs) {

        String destCurrencyCode = countryMasterRepository.findByCountryCode(destCountryCode);
        Double minRatePerKg = tarrifMasterRepository.findRatePerKg(weightInKg);
        if(minRatePerKg == null){
            throw new ServiceException(ServiceErrorCode.MINIMUM_RATE_NOT_SET, ServiceErrorMessage.MINIMUM_RATE_NOT_SET + " for weight : " + weightInKg);
        }

        String strNum = String.valueOf(weightInKg);
        int intNum = Integer.parseInt(strNum.substring(strNum.indexOf(".") + 1, strNum.indexOf(".") + 2));

        if (intNum > 0 && intNum <= 5) {
            weightInKg = Math.floor(weightInKg) + 0.5;
        }
        if (intNum > 5 && intNum <= 9) {
            weightInKg = Math.ceil(weightInKg);
        }
        double totalCost = weightInKg * minRatePerKg;
        Double customDutyIntrestRate = customdutyMasterRepository.findCustomDutyRate(totalCost);
        if(customDutyIntrestRate != null){
            totalCost = totalCost + (totalCost*customDutyIntrestRate)/100;
        }else{
            throw new ServiceException(ServiceErrorCode.CUSTOM_DUTY_NOT_SET, ServiceErrorMessage.CUSTOM_DUTY_NOT_SET + " for total cost : " + totalCost);
        }
        double exchangeRate = shipmentRateRepository.getExchangeRate("AE", destCurrencyCode, totalCost, new Date());

        return totalCost * exchangeRate;
    }
}