package dev.khalifa.gtrGenomicStudy.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class StandardTerms {
    HashMap<String, String> testDevelopment = new HashMap<String, String>();
    String[] testDevelopmentTerms = {
            "FDA-reviewed (has FDA test name)",
            "Manufactured (research use only; not FDA-reviewed)",
            "Modified FDA (has FDA-reviewed entry, but with lab modifications/field changes)",
            "Test developed by laboratory (no manufacturer test name)"
    };

    public StandardTerms() {
        for (int i=0; i < testDevelopmentTerms.length; i++){
            testDevelopment.put(testDevelopmentTerms[i], String.valueOf(i+1));
        }
    }
}
