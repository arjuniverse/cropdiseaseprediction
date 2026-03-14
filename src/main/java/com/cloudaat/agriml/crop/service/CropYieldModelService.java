package com.cloudaat.agriml.crop.service;

import com.cloudaat.agriml.crop.api.CropYieldRequest;
import com.cloudaat.agriml.crop.api.CropYieldResponse;
import com.cloudaat.agriml.dataset.CropDatasetLoader;
import org.springframework.stereotype.Service;

@Service
public class CropYieldModelService {

    private final CropDatasetLoader datasetLoader;

    public CropYieldModelService(CropDatasetLoader datasetLoader) {
        this.datasetLoader = datasetLoader;
    }

    /**
     * Tiny embedded regression-style model.
     * <p>
     * NOTE: In a real system these coefficients would be learned from data and
     * loaded from an external model file (e.g. ONNX, PMML, or a database).
     */
    public CropYieldResponse predictYield(CropYieldRequest request) {
        double cropBias = cropTypeBias(request.getCropType());

        double rainfall = Math.min(request.getSeasonalRainfallMm(), 1500.0) / 1000.0;
        double nitrogen = request.getNitrogenFertilizerKgPerHa() / 100.0;
        double soilCarbon = request.getSoilOrganicCarbonPct() / 3.0;
        double temperature = request.getAvgTemperatureC();
        double historical = request.getHistoricalYieldTonPerHa() != null
                ? request.getHistoricalYieldTonPerHa()
                : datasetLoader.getAverageYieldForCrop(request.getCropType()).orElse(2.5);

        // Penalise temperatures far from the 20–26°C sweet spot
        double tempPenalty = Math.max(0, Math.abs(23.0 - temperature) - 3.0) * 0.08;

        double baseIntercept = 1.2;

        double yieldTonPerHa = baseIntercept
                + 0.9 * rainfall
                + 0.7 * nitrogen
                + 0.5 * soilCarbon
                + 0.3 * historical
                + cropBias
                - tempPenalty;

        if (yieldTonPerHa < 0.3) {
            yieldTonPerHa = 0.3;
        }

        double totalProductionTons = yieldTonPerHa * request.getAreaHectares();

        String explanation = String.format(
                "Model used rainfall=%.1f mm, nitrogen=%.1f kg/ha, soil carbon=%.1f%%, temp=%.1f°C, historical yield=%.2f t/ha for crop '%s'.",
                request.getSeasonalRainfallMm(),
                request.getNitrogenFertilizerKgPerHa(),
                request.getSoilOrganicCarbonPct(),
                request.getAvgTemperatureC(),
                historical,
                request.getCropType()
        );

        return new CropYieldResponse(
                round(yieldTonPerHa, 2),
                round(totalProductionTons, 2),
                explanation
        );
    }

    private double cropTypeBias(String cropTypeRaw) {
        if (cropTypeRaw == null) {
            return 0.0;
        }
        String cropType = cropTypeRaw.trim().toLowerCase();
        return switch (cropType) {
            case "wheat" -> 0.4;
            case "rice" -> 0.6;
            case "maize", "corn" -> 0.3;
            case "soybean", "soy" -> 0.2;
            case "cotton" -> -0.1;
            default -> 0.0;
        };
    }

    private double round(double value, int places) {
        double factor = Math.pow(10, places);
        return Math.round(value * factor) / factor;
    }
}

