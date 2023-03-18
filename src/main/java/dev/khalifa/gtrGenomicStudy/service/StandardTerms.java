package dev.khalifa.gtrGenomicStudy.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class StandardTerms {
    HashMap<String, String> testDevelopment = new HashMap<String, String>();
    HashMap<String, String> indicationType = new HashMap<String, String>();
//    HashMap<String, String > diseaseSource = new HashMap<String, String>();
    String[] testDevelopmentTerms = {
            "FDA-reviewed (has FDA test name)",
            "Manufactured (research use only; not FDA-reviewed)",
            "Modified FDA (has FDA-reviewed entry, but with lab modifications/field changes)",
            "Test developed by laboratory (no manufacturer test name)"
    };

    String[] indicationTypeTerms = {
            "Infectious disease",
            "Pharmacological response",
            "Disease",
            "Finding",
            "Named protein variant",
            "Blood group",
            "Congenital muscular dystrophy-dystroglycanopathy with brain and eye anomalies type A5",
            "Hyperphenylalaninemia, BH4-deficient, D",
            "Autoimmune disease",
            "Common variable immunodeficiency",
            "Diabetes mellitus",
            "Autoimmune polyglandular syndrome type 1, with reversible metaphyseal dysplasia",
            "Maturity onset diabetes mellitus in young",
            "Neonatal diabetes mellitus",
            "Ataxia, combined cerebellar and peripheral, with hearing loss and diabetes mellitus",
            "Microcephaly, short stature, and impaired glucose metabolism 1",
            "Primrose syndrome",
            "Beckwith-Wiedemann syndrome",
            "Hexosaminidase A deficiency, adult type",
            "Juvenile (Subacute) Hexosaminidase A Deficiency",
            "Hypermethioninemia with s-adenosylhomocysteine hydrolase deficiency",
            "Fetal hemoglobin quantitative trait locus 1"
    };

/*    String[] diseaseSourceNames = {
            "NCBI curation",
            "MONDO",
            "Orphanet",
            "Human Phenotype Ontology",
            "OMIM phenotypic series",
            "PharmGKB",
            "OMIM",
            "Clinical Pharmacogenetics Implementation Consortium",
            "GeneReviews"
    };*/

    public StandardTerms() {
        for (int i=0; i < testDevelopmentTerms.length; i++){
            testDevelopment.put(testDevelopmentTerms[i], String.valueOf(i+1));
        }

        for (int i=0; i < indicationTypeTerms.length; i++){
            indicationType.put(indicationTypeTerms[i], String.valueOf(i+1));
        }



    }
}
