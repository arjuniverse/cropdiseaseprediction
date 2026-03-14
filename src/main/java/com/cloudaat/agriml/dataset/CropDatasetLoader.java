package com.cloudaat.agriml.dataset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Loads crop production data from Kaggle dataset (Crop Production in India).
 * Run scripts/download_dataset.py first to download the dataset into data/crop-production-in-india/
 */
@Component
public class CropDatasetLoader {

    private static final Logger log = LoggerFactory.getLogger(CropDatasetLoader.class);

    private final Map<String, Double> avgYieldByCrop = new HashMap<>();

    @PostConstruct
    public void load() {
        Path dataDir = Paths.get("data", "crop-production-in-india").toAbsolutePath();
        if (!Files.isDirectory(dataDir)) {
            log.info("Dataset not found at {}. Run: python scripts/download_dataset.py", dataDir);
            return;
        }
        try {
            Files.list(dataDir)
                    .filter(p -> p.toString().toLowerCase().endsWith(".csv"))
                    .findFirst()
                    .ifPresent(this::parseCsv);
        } catch (IOException e) {
            log.warn("Could not load dataset: {}", e.getMessage());
        }
    }

    private void parseCsv(Path csvPath) {
        try (BufferedReader reader = Files.newBufferedReader(csvPath)) {
            org.apache.commons.csv.CSVParser parser = org.apache.commons.csv.CSVParser.parse(
                    reader,
                    org.apache.commons.csv.CSVFormat.DEFAULT.builder()
                            .setHeader()
                            .setSkipHeaderRecord(true)
                            .setIgnoreHeaderCase(true)
                            .build()
            );
            var headers = parser.getHeaderNames();
            String cropCol = firstOf(headers, "Crop", "crop");
            String areaCol = firstOf(headers, "Area", "area");
            String prodCol = firstOf(headers, "Production", "production");
            if (cropCol == null || areaCol == null || prodCol == null) {
                log.warn("Dataset missing Crop/Area/Production columns. Found: {}", headers);
                return;
            }
            Map<String, java.util.List<Double>> yieldsByCrop = new HashMap<>();
            for (org.apache.commons.csv.CSVRecord record : parser) {
                String crop = normalize(get(record, cropCol));
                if (crop == null) continue;
                double area = parseDouble(get(record, areaCol) != null ? get(record, areaCol) : "0");
                double production = parseDouble(get(record, prodCol) != null ? get(record, prodCol) : "0");
                if (area > 0 && production >= 0) {
                    double yieldTonPerHa = production / area;
                    yieldsByCrop.computeIfAbsent(crop, k -> new java.util.ArrayList<>()).add(yieldTonPerHa);
                }
            }
            yieldsByCrop.forEach((crop, yields) -> {
                double avg = yields.stream().mapToDouble(Double::doubleValue).average().orElse(0);
                avgYieldByCrop.put(crop, Math.round(avg * 100.0) / 100.0);
            });
            log.info("Loaded dataset: {} crops from {}", avgYieldByCrop.size(), csvPath.getFileName());
        } catch (Exception e) {
            log.warn("Failed to parse {}: {} (dataset columns may differ)", csvPath.getFileName(), e.getMessage());
        }
    }

    private String firstOf(java.util.List<String> headers, String... names) {
        for (String n : names) {
            if (headers.stream().anyMatch(h -> h.equalsIgnoreCase(n))) {
                return headers.stream().filter(h -> h.equalsIgnoreCase(n)).findFirst().orElse(null);
            }
        }
        return null;
    }

    private String get(org.apache.commons.csv.CSVRecord record, String col) {
        try {
            return record.get(col);
        } catch (Exception e) {
            return null;
        }
    }

    private String normalize(String s) {
        if (s == null || s.isBlank()) return null;
        return s.trim().toLowerCase();
    }

    private double parseDouble(String val) {
        try {
            if (val == null || val.isBlank()) return 0;
            return Double.parseDouble(val.replaceAll("[^0-9.\\-]", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Returns average yield (ton/ha) for the crop from the dataset, if available.
     */
    public Optional<Double> getAverageYieldForCrop(String cropType) {
        if (cropType == null || cropType.isBlank()) return Optional.empty();
        String key = cropType.trim().toLowerCase();
        return Optional.ofNullable(avgYieldByCrop.get(key));
    }

    public boolean isLoaded() {
        return !avgYieldByCrop.isEmpty();
    }
}
