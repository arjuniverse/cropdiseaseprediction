package com.cloudaat.agriml.crop.api;

public class CropYieldResponse {

    private double predictedYieldTonPerHa;
    private double totalProductionTons;
    private String explanation;

    public CropYieldResponse() {
    }

    public CropYieldResponse(double predictedYieldTonPerHa, double totalProductionTons, String explanation) {
        this.predictedYieldTonPerHa = predictedYieldTonPerHa;
        this.totalProductionTons = totalProductionTons;
        this.explanation = explanation;
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
}
