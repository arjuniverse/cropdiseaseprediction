package com.cloudaat.agriml.disease.service;

import com.cloudaat.agriml.disease.api.PlantDiseaseRequest;
import com.cloudaat.agriml.disease.api.PlantDiseaseResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PlantDiseaseModelService {

    private static final String LABEL_HEALTHY = "HEALTHY";
    private static final String LABEL_LEAF_BLIGHT = "LEAF_BLIGHT";
    private static final String LABEL_POWDERY_MILDEW = "POWDERY_MILDEW";

    /**
     * Very small multi-class logistic regression–style model.
     * <p>
     * Features are hand-engineered boolean / numeric values extracted from the request.
     * Coefficients are hand-tuned, but structured as if they were learned from data.
     */
    public PlantDiseaseResponse detectDisease(PlantDiseaseRequest request) {
        double[] features = buildFeatures(request);

        double scoreHealthy = dot(HEALTHY_WEIGHTS, features) + HEALTHY_BIAS;
        double scoreBlight = dot(LEAF_BLIGHT_WEIGHTS, features) + LEAF_BLIGHT_BIAS;
        double scoreMildew = dot(POWDERY_MILDEW_WEIGHTS, features) + POWDERY_MILDEW_BIAS;

        Map<String, Double> probs = softmax(scoreHealthy, scoreBlight, scoreMildew);

        String predicted = probs.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(LABEL_HEALTHY);

        String explanation = buildExplanation(predicted, request, probs);

        return new PlantDiseaseResponse(predicted, probs, explanation);
    }

    // [f0] humidityHigh, [f1] humidityLow, [f2] tempWarm, [f3] tempCool,
    // [f4] spotsPresent, [f5] spotsRusty, [f6] spotsWhite, [f7] leafYellow, [f8] leafBrown
    private double[] buildFeatures(PlantDiseaseRequest r) {
        double humidityHigh = r.getHumidityPct() >= 75 ? 1.0 : 0.0;
        double humidityLow = r.getHumidityPct() <= 40 ? 1.0 : 0.0;

        double tempWarm = r.getTemperatureC() >= 22 && r.getTemperatureC() <= 30 ? 1.0 : 0.0;
        double tempCool = r.getTemperatureC() <= 20 ? 1.0 : 0.0;

        boolean hasSpots = Boolean.TRUE.equals(r.getHasSpots());
        String spotsColor = r.getSpotsColor() == null ? "" : r.getSpotsColor().trim().toUpperCase();

        double spotsPresent = hasSpots ? 1.0 : 0.0;
        double spotsRusty = hasSpots && spotsColor.contains("RUST") ? 1.0 : 0.0;
        double spotsWhite = hasSpots && spotsColor.contains("WHITE") ? 1.0 : 0.0;

        String leafColor = r.getLeafColor() == null ? "" : r.getLeafColor().trim().toUpperCase();
        double leafYellow = leafColor.contains("YELLOW") ? 1.0 : 0.0;
        double leafBrown = leafColor.contains("BROWN") ? 1.0 : 0.0;

        return new double[]{
                humidityHigh,
                humidityLow,
                tempWarm,
                tempCool,
                spotsPresent,
                spotsRusty,
                spotsWhite,
                leafYellow,
                leafBrown
        };
    }

    // These would come from a trained model in a real application
    private static final double[] HEALTHY_WEIGHTS = { -0.3, 0.0, 0.2, 0.1, -0.6, -0.8, -0.8, -0.5, -0.4 };
    private static final double HEALTHY_BIAS = 0.5;

    private static final double[] LEAF_BLIGHT_WEIGHTS = { 0.4, 0.0, 0.2, 0.0, 0.7, 0.8, 0.1, 0.5, 0.6 };
    private static final double LEAF_BLIGHT_BIAS = -0.2;

    private static final double[] POWDERY_MILDEW_WEIGHTS = { 0.3, 0.0, 0.5, 0.0, 0.6, 0.0, 0.9, 0.2, 0.2 };
    private static final double POWDERY_MILDEW_BIAS = -0.3;

    private double dot(double[] w, double[] x) {
        double s = 0.0;
        for (int i = 0; i < w.length; i++) {
            s += w[i] * x[i];
        }
        return s;
    }

    private Map<String, Double> softmax(double sHealthy, double sBlight, double sMildew) {
        double max = Math.max(sHealthy, Math.max(sBlight, sMildew));
        double eH = Math.exp(sHealthy - max);
        double eB = Math.exp(sBlight - max);
        double eM = Math.exp(sMildew - max);
        double sum = eH + eB + eM;

        Map<String, Double> result = new HashMap<>();
        result.put(LABEL_HEALTHY, round(eH / sum, 3));
        result.put(LABEL_LEAF_BLIGHT, round(eB / sum, 3));
        result.put(LABEL_POWDERY_MILDEW, round(eM / sum, 3));
        return result;
    }

    private String buildExplanation(String predicted, PlantDiseaseRequest r, Map<String, Double> probs) {
        return String.format(
                "Predicted %s for crop '%s' with humidity=%.1f%%, temp=%.1f°C, leafColor=%s, spots=%s (color=%s). Probabilities: %s",
                predicted,
                r.getCropType(),
                r.getHumidityPct(),
                r.getTemperatureC(),
                r.getLeafColor(),
                String.valueOf(r.getHasSpots()),
                r.getSpotsColor(),
                probs
        );
    }

    private double round(double value, int places) {
        double factor = Math.pow(10, places);
        return Math.round(value * factor) / factor;
    }
}

