package com.newage.fsldelivers.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="fsl_custom_tax_rate")
public class CustomdutyMaster extends Auditable<String>  {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long id;

	@Column(name="from_cost")
	private double fromCost;

	@Column(name="to_cost")
	private double toCost;

	@Column(name="rate_of_interest")
	private double rateOfInterest;

}