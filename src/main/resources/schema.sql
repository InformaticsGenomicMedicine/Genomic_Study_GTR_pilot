CREATE TABLE IF NOT EXISTS Gtr_Entry (
--     id INTEGER AUTO_INCREMENT,  -- for H2
                                       id SERIAL PRIMARY KEY,
                                       test_accession_ver text,
                                       name_of_laboratory text,
                                       name_of_institution text,
                                       facility_state text,
                                       facility_postcode text,
                                       facility_country text,
                                       CLIA_number text,
                                       state_licenses text,
                                       state_license_numbers text,
                                       lab_test_id text,
                                       last_touch_date text,
                                       lab_test_name text,
                                       manufacturer_test_name text,
                                       test_development text,
                                       lab_unique_code text,
                                       condition_identifiers text,
                                       indication_types text,
                                       inheritances text,
                                       method_categories text,
                                       methods text,
                                       platforms text,
                                       genes text,
                                       drug_responses text,
                                       now_current text,
                                       test_curr_stat text,
                                       test_pub_stat text,
                                       lab_curr_stat text,
                                       lab_pub_stat text,
                                       test_create_date TIMESTAMP,
                                       test_deletion_date TIMESTAMP
--     , primary key (id) -- for H2
);


-- to load data from the TSV file

COPY gtr_entry (test_accession_ver,
name_of_laboratory,
name_of_institution,
facility_state,
facility_postcode,
facility_country,
CLIA_number,
state_licenses,
state_license_numbers,
lab_test_id,
last_touch_date,
lab_test_name,
manufacturer_test_name,
test_development,
lab_unique_code,
condition_identifiers,
indication_types,
inheritances,
method_categories,
methods,
platforms,
genes,
drug_responses,
now_current,
test_curr_stat,
test_pub_stat,
lab_curr_stat,
lab_pub_stat,
test_create_date,
test_deletion_date)
FROM 'C:\Users\M250419\Downloads\test_version'--'C:\Users\path\to\NCBI\GTR\test_version' --after un zipping it
DELIMITER E'\t'
CSV HEADER;

-- Table: public.gtr_xml_Excel

-- DROP TABLE IF EXISTS public."gtr_ftp_xml";

CREATE TABLE IF NOT EXISTS public."gtr_ftp_xml"
(
    id double precision,
    "GTRAccession" character varying(255) COLLATE pg_catalog."default",
    "Version" character varying(255) COLLATE pg_catalog."default",
    "TestName" character varying(255) COLLATE pg_catalog."default",
    "TestShortName" character varying(255) COLLATE pg_catalog."default",
    "TestStrategyDescription" character varying(255) COLLATE pg_catalog."default",
    "TestStrategyPMID" double precision,
    "TestStrategyURL" character varying(255) COLLATE pg_catalog."default",
    "TestStrategyCitationText" character varying(255) COLLATE pg_catalog."default",
    "TestCodesURL" character varying(255) COLLATE pg_catalog."default",
    "TestStrategyLOINC" character varying(255) COLLATE pg_catalog."default",
    "TestStrategyCPTCode" character varying(255) COLLATE pg_catalog."default",
    "MethodTopCategoryValue" character varying(255) COLLATE pg_catalog."default",
    "MethodCategoryValue" character varying(255) COLLATE pg_catalog."default",
    "MethodCategorycode" character varying(255) COLLATE pg_catalog."default",
    "MethodologyValue" character varying(255) COLLATE pg_catalog."default",
    "MethodologyInstrument" character varying(255) COLLATE pg_catalog."default",
    "MethodPlatform" character varying(255) COLLATE pg_catalog."default",
    "MethodProtocolDescription" text COLLATE pg_catalog."default",
    "MethodProtocolPMID" double precision,
    "MethodProtocolURL" character varying(255) COLLATE pg_catalog."default",
    "MethodProtocolCitationText" character varying(255) COLLATE pg_catalog."default",
    "MethodConfirmation" character varying(255) COLLATE pg_catalog."default",
    "Exons" character varying(255) COLLATE pg_catalog."default",
    "ExonQualifier" character varying(255) COLLATE pg_catalog."default",
    "ExonGeneID" character varying(255) COLLATE pg_catalog."default"
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."gtr_ftp_xml"
    OWNER to postgres;