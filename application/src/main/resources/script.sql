

CREATE TABLE fsl_user_contact (
    "id"                     INTEGER NOT NULL,
    "delivery_address1"   VARCHAR2(100 CHAR) NOT NULL,
    "delivery_address2"     VARCHAR2(100 CHAR),
    "area"                   CLOB,CREATE TABLE fsl_user_data (
                                      "id"                   INTEGER NOT NULL,
                                      "first_name"              VARCHAR2(20 CHAR) NOT NULL,
                                      "last_name"            VARCHAR2(10 CHAR),
                                      "identification_type"     VARCHAR2(10 CHAR),
                                      "nationality"             VARCHAR2(10 CHAR),
                                      "identification_number"   VARCHAR2(10 CHAR),
                                      "dob"                     DATE,
                                      "user_name"               VARCHAR2(30 CHAR) NOT NULL,
                                      "password "             VARCHAR2(50 CHAR) NOT NULL,
                                      "email"                   VARCHAR2(25 CHAR) NOT NULL,
                                      "status"                  VARCHAR2(8 CHAR) NOT NULL,
                                      "created_date"            DATE NOT NULL,
                                      "modified_date"            DATE  NOT NULL,
                                      "created_by"              VARCHAR2(20 CHAR) NOT NULL,
                                      "modified_by"         VARCHAR2(20 CHAR) NOT NULL
                                      );

                                  ALTER TABLE fsl_user_data ADD CONSTRAINT fsl_user_data_pk PRIMARY KEY ( "id");
    "postalcode"          VARCHAR2(10 CHAR),
    "state_code"             VARCHAR2(10 CHAR),
    "country_code"           VARCHAR2(10 CHAR),
    "country"                INTEGER,
    "phone_number"           VARCHAR2(10 CHAR),
    "mobile_number"          VARCHAR2(13 CHAR),
    "user_id"                INTEGER NOT NULL,
    "created_date"            DATE NOT NULL,
    "modified_date"            DATE  NOT NULL,
    "created_by"              VARCHAR2(20 CHAR) NOT NULL,
    "modified_by"         VARCHAR2(20 CHAR) NOT NULL
);

ALTER TABLE fsl_user_contact ADD CONSTRAINT fsl_user_contact_pk PRIMARY KEY ("id");

ALTER TABLE fsl_user_contact
    ADD CONSTRAINT fsl_user_data_fk FOREIGN KEY ( "user_id" )
        REFERENCES fsl_user_data ( "id" );