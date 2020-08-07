package com.newage.fsldelivers.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="FSL_TARRIF_MASTER")
public class TarrifMaster {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long id;

	@Column(name="FROM_WEIGHT", precision = 15, scale = 0, columnDefinition = "NUMBER (10)")
	private double fromWt;

	@Column(name="TO_WEIGHT", precision = 15, scale = 0, columnDefinition = "NUMBER (10)")
	private double toWt;

	@Column(name="RATE_PER_KG", precision = 15, scale = 0, columnDefinition = "NUMBER (10)")
	private double ratePerKg;

}