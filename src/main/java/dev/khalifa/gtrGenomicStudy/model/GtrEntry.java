package dev.khalifa.gtrGenomicStudy.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public record GtrEntry (
        @Id
        Integer id,
        String test_accession_ver ,
        String name_of_laboratory ,
        String name_of_institution ,
        String facility_state ,
        String facility_postcode ,
        String facility_country ,
        @Column(value = "CLIA_number")
        String clia_number,
        String state_licenses,
        String state_license_numbers,
        String lab_test_id,
        String last_touch_date,
        String lab_test_name,
        String manufacturer_test_name,
        String test_development,
        String lab_unique_code,
        String condition_identifiers,
        String indication_types,
        String inheritances,
        String method_categories,
        String methods,
        String platforms,
        String genes,
        String drug_responses,
        String now_current,
        String test_currStat,
        String test_pubStat,
        String lab_currStat,
        String lab_pubStat,
        String test_create_date,
        String test_deletion_date
){}
