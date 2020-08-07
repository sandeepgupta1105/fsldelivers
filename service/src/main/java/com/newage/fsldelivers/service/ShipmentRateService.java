package com.newage.fsldelivers.service;

import com.newage.fsldelivers.model.CountryMaster;
import com.newage.fsldelivers.model.TarrifMaster;
import com.newage.fsldelivers.repository.CountryMasterRepository;
import com.newage.fsldelivers.repository.ShipmentRateRepository;
import com.newage.fsldelivers.repository.TarrifMasterRepository;
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

	private static final Logger logger = LogManager.getLogger(ShipmentRateService.class);
	public Double getShipmentRate(String destCountryCode, double wtInKg, double lbs) {


		String destCurrencyCode = countryMasterRepository.findByCountryCode(destCountryCode);

		List<TarrifMaster> tm = tarrifMasterRepository.findRatePerKgObj(wtInKg);
		double minRatePerKg = tarrifMasterRepository.findRatePerKg(wtInKg);
		//long minRatePerKg = 50;
		Double d = wtInKg;
		String strNum = String.valueOf(d);
		strNum = strNum.substring( strNum.indexOf ( "." ) +1);
		int intNum = Integer.parseInt(strNum.substring(0,1));

		if(intNum > 0 && intNum <= 5){
			d = Math.floor(d) + 0.5;
		}
		if(intNum > 5 && intNum <= 9){
			d = Math.ceil(d);
		}
		double totalAmt = d * minRatePerKg;
		double exchangeRate = shipmentRateRepository.getExchangeRate("AE", destCurrencyCode, totalAmt, new Date());

		return totalAmt*exchangeRate;
	}
}