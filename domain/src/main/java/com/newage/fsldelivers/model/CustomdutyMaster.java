package com.newage.fsldelivers.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="FSL_CUSTOM_TAX_RATE")
public class CustomdutyMaster {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private long id;

	@Column(name="FROM_COST", precision = 15, scale = 10, columnDefinition = "NUMBER (15)")
	private Double fromCost;

	@Column(name="TO_COST", precision = 15, scale = 10, columnDefinition = "NUMBER (15)")
	private Double toCost;

	@Column(name="RATE_OF_INTEREST", precision = 15, scale = 10, columnDefinition = "NUMBER (15)")
	private Double rateOfInterest;

}