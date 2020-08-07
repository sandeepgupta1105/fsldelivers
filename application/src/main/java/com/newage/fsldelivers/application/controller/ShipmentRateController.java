package com.newage.fsldelivers.application.controller;

import com.newage.fsldelivers.service.ShipmentRateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ShipmentRateController {

	private static final Logger logger = LogManager.getLogger(ShipmentRateController.class);

	@Autowired
	private ShipmentRateService shipmentRateService;

	@GetMapping(value = "/shipmentrate/{destcurr}/{wtkg}/{lbs}")
	public Double getShipmentRate(@PathVariable("destcurr") String destCurrency,
		@PathVariable("wtkg") double wtkg, @PathVariable("lbs") double lbs){

		logger.info("Inside ShipmentRateController -> getShipmentRate method called.");
		logger.info("Dest : " + destCurrency + ", wt in Kg : " + wtkg + " and wt in Lbs : " +lbs);
		return shipmentRateService.getShipmentRate(destCurrency, wtkg, lbs);

	}
}