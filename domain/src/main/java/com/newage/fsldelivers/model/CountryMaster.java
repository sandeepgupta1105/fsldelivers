package com.newage.fsldelivers.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "country_master", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code"}, name = "UK_COUNTRY_COUNTRYCODE"),
        @UniqueConstraint(columnNames = {"country_name"}, name = "UK_COUNTRY_COUNTRYNAME")})
public class CountryMaster extends Auditable<String>  {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id;

    @Version
    @Column(name = "version_lock")
    long versionLock;

    @Column(name = "code", nullable = false, length = 10)
    String countryCode;

    @Column(name = "country_name", nullable = false, length = 100)
    String countryName;

    @Column(name = "locale", length = 10)
    String locale;

    @Column(name = "currency_code", length = 10)
    String currencyCode;

    @Column(name = "region_code", length = 100)
    String regionCode;

    @Column(name = "nationality", length = 100)
    String nationality;

    @Column(name = "weight_cbm")
    Double weightOrCbm;

    @Column(name = "image")
    @Lob
    byte[] image;

}
