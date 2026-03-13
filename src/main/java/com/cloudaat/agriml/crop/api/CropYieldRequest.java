package com.cloudaat.agriml.crop.api;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CropYieldRequest {

    @NotBlank
    private String cropType;

    @Min(0)
    @Max(10000)
    private double areaHectares;

    @Min(0)
    @Max(5000)
    private double seasonalRainfallMm;

    @Min(0)
    @Max(500)
    private double nitrogenFertilizerKgPerHa;

    @Min(0)
    @Max(10)
    private double soilOrganicCarbonPct;

    @Min(-10)
    @Max(50)
    private double avgTemperatureC;

    private Double historicalYieldTonPerHa;

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
}
