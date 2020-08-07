package com.newage.fsldelivers.model;


import javax.persistence.*;

import lombok.*;

@Getter
@Setter
@Entity
@Table(name="FSL_USER_CONTACT")
public class UserContact extends Auditable<String>  {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long id;

	@Column(name="address",length = 500)
	private String address;

	@Column(name="city")
	private String city;

	@Column(name="postal_code",length = 10)
	private Integer postalcode;

	@Column(name="country_id")
	private Long countryId;

	@Column(name="phone_number",length = 10)
	private String phoneNumber;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

}