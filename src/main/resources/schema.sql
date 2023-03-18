/*Please note the important note from the NCBI-GTR website:
    " If you distribute or copy data from the NIH Genetic Testing Registry, we ask that you provide attribution to GTR as a data source in publications and websites by:

    linking to the GTR website: http://www.ncbi.nlm.nih.gov/gtr/ and/or
    citing this publication: http://nar.oxfordjournals.org/content/41/D1/D925.full"*/

/*
    This tool does not aim to distribute or copy data from NIH Genetic Testing Registry, but to show case how informatics tools can maximize the benefit from these valuable data.
        NIH work is much appreacheated, thanks a lot
*/



DROP TABLE IF EXISTS Gtr_Entry;
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
--     source: https://ftp.ncbi.nlm.nih.gov/pub/GTR/data/test_version.gz
DELIMITER E'\t'
CSV HEADER;

DROP TABLE IF EXISTS disease;
CREATE TABLE IF NOT EXISTS disease (
--     id INTEGER AUTO_INCREMENT,  -- for H2
                                         id SERIAL PRIMARY KEY,
                                         disease_name text,
                                         source_name text,
                                         concept_id text,
                                         source_id text,
                                         disease_mim text,
                                         last_modified text,
                                         category text
--     , primary key (id) -- for H2
);

-- to load data from the TSV file

COPY disease (  disease_name,
                source_name,
                concept_id,
                source_id,
                disease_mim,
                last_modified,
                category
    )
FROM 'C:\Users\M250419\Downloads\disease_names.txt'--'C:\Users\path\to\NCBI\GTR\disease_names.txt' --after un zipping it
    --source: https://ftp.ncbi.nlm.nih.gov/pub/GTR/standard_terms/disease_names.txt
DELIMITER E'\t'
CSV HEADER;



-- Table: public.Copy Of gtr_ftp_xml

-- DROP TABLE IF EXISTS public."gtr_ftp_xml";

/*CREATE TABLE IF NOT EXISTS public."gtr_ftp_xml"
(
    "id" text,
    "GTRAccession" text,
    "Version" text,
    "TestName" text,
    "TestShortName" text,
    "TestStrategyPMID" text,
    "TestStrategyURL" text,
    "TestStrategyCitationText" text,
    "TestCodesURL" text,
    "TestStrategyLOINC" text,
    "TestStrategyCPTCode" text,
    "MethodTopCategoryValue" text,
    "MethodCategoryValue" text,
    "MethodCategorycode" text,
    "MethodologyValue" text,
    "MethodologyInstrument" text,
    "MethodPlatform" text,
    "MethodProtocolPMID" text,
    "MethodProtocolURL" text,
    "MethodProtocolCitationText" text,
    "MethodConfirmation" text,
    "Exons" text,
    "ExonQualifier" text,
    "ExonGeneID" text
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."gtr_ftp_xml"
    OWNER to postgres;*/

-- Table: public.gtr_xml_Excel

-- DROP TABLE IF EXISTS public."gtr_ftp_xml";

/*
COPY "gtr_ftp_xml" (
            "id",
            "GTRAccession",
            "Version",
            "TestName",
            "TestShortName",
            "TestStrategyPMID",
            "TestStrategyURL",
            "TestStrategyCitationText",
            "TestCodesURL",
            "TestStrategyLOINC",
            "TestStrategyCPTCode",
            "MethodTopCategoryValue",
            "MethodCategoryValue",
            "MethodCategorycode",
            "MethodologyValue",
            "MethodologyInstrument",
            "MethodPlatform",
            "MethodProtocolPMID",
            "MethodProtocolURL",
            "MethodProtocolCitationText",
            "MethodConfirmation",
            "Exons",
            "ExonQualifier",
            "ExonGeneID"
    )
FROM 'C:\Users\M250419\Downloads\gtr_ftp_xml.txt'--'C:\Users\M250419\Downloads\gtr_ftp_xml.tvs' --after un zipping it
DELIMITER E'\t'
CSV HEADER;*/
