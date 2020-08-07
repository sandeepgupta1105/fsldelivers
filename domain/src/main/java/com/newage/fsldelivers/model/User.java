package com.newage.fsldelivers.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.newage.fsldelivers.enums.EIdentificationType;
import com.newage.fsldelivers.enums.EStatus;

import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "fsl_user_data", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "email" }, name = "UK_USER_EMAIL") })
public class User extends Auditable<String>  {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name = "dob")
	private Date dob;

	@Column(name = "email", nullable = false, length = 25)
	private String email;

	@Column(name = "first_name", nullable = false,length = 20)
	private String firstName;

	@Column(name = "identification_type",length = 20)
	@Enumerated(EnumType.STRING)
	private EIdentificationType identificationType;

	@Column(name = "identification_number",length = 50)
	private String identificationNumber;

	@Column(name = "last_name",length = 10)
	private String lastName;

	@Column(name = "nationality",length = 10)
	private String nationality;

	@Column(name = "password", nullable = false,length = 100)
	private String password;
	
	@Column(name = "reset_code", nullable = false,length = 50)
	private String resetCode;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "reset_code_expiry")
	private Date resetCodeExpiry;

	@Column(name = "customer_id", nullable = false,length = 50)
	private String customerId;

	@Column(name = "status", nullable = false, length = 8)
	@Enumerated(EnumType.STRING)
	private EStatus status;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<UserContact> userContacts;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

}