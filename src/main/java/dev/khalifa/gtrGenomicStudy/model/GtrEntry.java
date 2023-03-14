package dev.khalifa.gtrGenomicStudy.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public record GtrEntry (
        @Id
        Integer id,
        @Column(value = "test_accession_ver")
        String testAccessionVer ,
        @Column(value = "name_of_laboratory")
        String nameOfLaboratory ,
        @Column(value = "name_of_institution")
        String nameOfInstitution ,
        @Column(value = "facility_state")
        String facilityState ,
        @Column(value = "facility_postcode")
        String facilityPostcode ,
        @Column(value = "facility_country")
        String facilityCountry ,
//        @Column(value = "CLIA_number")
        @Column(value = "clia_number")
        String cliaNumber,
        @Column(value = "state_licenses")
        String stateLicenses,
        @Column(value = "state_license_numbers")
        String stateLicenseNumbers,
        @Column(value = "lab_test_id")
        String labTestId,
        @Column(value = "last_touch_date")
        String lastTouchDate,
        @Column(value = "lab_test_name")
        String labTestName,
        @Column(value = "manufacturer_test_name")
        String manufacturerTestName,
        @Column(value = "test_development")
        String testDevelopment,
        @Column(value = "lab_unique_code")
        String labUniqueCode,
        @Column(value = "condition_identifiers")
        String conditionIdentifiers,
        @Column(value = "indication_types")
        String indicationTypes,

        String inheritances,
        @Column(value = "method_categories")
        String methodCategories,

        String methods,
        String platforms,
        String genes,
        @Column(value = "drug_responses")
        String drugResponses,
        @Column(value = "now_current")
        String nowCurrent,
        @Column(value = "test_curr_stat")
        String testCurrStat,
        @Column(value = "test_pub_stat")
        String testPubStat,
        @Column(value = "lab_curr_stat")
        String labCurrStat,
        @Column(value = "lab_pub_stat")
        String labPubStat,
        @Column(value = "test_create_date")
        String testCreateDate,
        @Column(value = "test_deletion_date")
        String testDeletionDate
){}
