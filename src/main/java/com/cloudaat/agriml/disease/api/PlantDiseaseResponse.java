package com.cloudaat.agriml.disease.api;

import java.util.Map;

public class PlantDiseaseResponse {

    private String predictedLabel;
    private Map<String, Double> labelProbabilities;
    private String explanation;

    public PlantDiseaseResponse() {
    }

    public PlantDiseaseResponse(String predictedLabel, Map<String, Double> labelProbabilities, String explanation) {
        this.predictedLabel = predictedLabel;
        this.labelProbabilities = labelProbabilities;
        this.explanation = explanation;
    }

    public String getPredictedLabel() {
        return predictedLabel;
    }

    public void setPredictedLabel(String predictedLabel) {
        this.predictedLabel = predictedLabel;
    }

    public Map<String, Double> getLabelProbabilities() {
        return labelProbabilities;
    }

    public void setLabelProbabilities(Map<String, Double> labelProbabilities) {
        this.labelProbabilities = labelProbabilities;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
