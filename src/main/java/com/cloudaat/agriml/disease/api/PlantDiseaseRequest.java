package com.cloudaat.agriml.disease.api;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PlantDiseaseRequest {

    @NotBlank
    private String cropType;

    @NotBlank
    private String leafColor;

    @NotNull
    private Boolean hasSpots;

    private String spotsColor;

    @Min(0)
    @Max(100)
    private double humidityPct;

    @Min(-10)
    @Max(50)
    private double temperatureC;

    @Min(0)
    @Max(100)
    private double soilMoisturePct;

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public String getLeafColor() {
        return leafColor;
    }

    public void setLeafColor(String leafColor) {
        this.leafColor = leafColor;
    }

    public Boolean getHasSpots() {
        return hasSpots;
    }

    public void setHasSpots(Boolean hasSpots) {
        this.hasSpots = hasSpots;
    }

    public String getSpotsColor() {
        return spotsColor;
    }

    public void setSpotsColor(String spotsColor) {
        this.spotsColor = spotsColor;
    }

    public double getHumidityPct() {
        return humidityPct;
    }

    public void setHumidityPct(double humidityPct) {
        this.humidityPct = humidityPct;
    }

    public double getTemperatureC() {
        return temperatureC;
    }

    public void setTemperatureC(double temperatureC) {
        this.temperatureC = temperatureC;
    }

    public double getSoilMoisturePct() {
        return soilMoisturePct;
    }

    public void setSoilMoisturePct(double soilMoisturePct) {
        this.soilMoisturePct = soilMoisturePct;
    }
}
