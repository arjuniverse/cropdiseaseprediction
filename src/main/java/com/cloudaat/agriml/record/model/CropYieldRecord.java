package com.cloudaat.agriml.record.model;

import java.time.LocalDateTime;

/**
 * Stored crop yield prediction record for CRUD operations.
 */
public class CropYieldRecord {

    private Long id;
    private String cropType;
    private double areaHectares;
    private double seasonalRainfallMm;
    private double nitrogenFertilizerKgPerHa;
    private double soilOrganicCarbonPct;
    private double avgTemperatureC;
    private Double historicalYieldTonPerHa;

    private double predictedYieldTonPerHa;
    private double totalProductionTons;
    private String explanation;
    private LocalDateTime createdAt;

    public CropYieldRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public double getAreaHectares() {
        return areaHectares;
    }

    public void setAreaHectares(double areaHectares) {
        this.areaHectares = areaHectares;
    }

    public double getSeasonalRainfallMm() {
        return seasonalRainfallMm;
    }

    public void setSeasonalRainfallMm(double seasonalRainfallMm) {
        this.seasonalRainfallMm = seasonalRainfallMm;
    }

    public double getNitrogenFertilizerKgPerHa() {
        return nitrogenFertilizerKgPerHa;
    }

    public void setNitrogenFertilizerKgPerHa(double nitrogenFertilizerKgPerHa) {
        this.nitrogenFertilizerKgPerHa = nitrogenFertilizerKgPerHa;
    }

    public double getSoilOrganicCarbonPct() {
        return soilOrganicCarbonPct;
    }

    public void setSoilOrganicCarbonPct(double soilOrganicCarbonPct) {
        this.soilOrganicCarbonPct = soilOrganicCarbonPct;
    }

    public double getAvgTemperatureC() {
        return avgTemperatureC;
    }

    public void setAvgTemperatureC(double avgTemperatureC) {
        this.avgTemperatureC = avgTemperatureC;
    }

    public Double getHistoricalYieldTonPerHa() {
        return historicalYieldTonPerHa;
    }

    public void setHistoricalYieldTonPerHa(Double historicalYieldTonPerHa) {
        this.historicalYieldTonPerHa = historicalYieldTonPerHa;
    }

    public double getPredictedYieldTonPerHa() {
        return predictedYieldTonPerHa;
    }

    public void setPredictedYieldTonPerHa(double predictedYieldTonPerHa) {
        this.predictedYieldTonPerHa = predictedYieldTonPerHa;
    }

    public double getTotalProductionTons() {
        return totalProductionTons;
    }

    public void setTotalProductionTons(double totalProductionTons) {
        this.totalProductionTons = totalProductionTons;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
