package com.cloudaat.agriml.record.service;

import com.cloudaat.agriml.crop.api.CropYieldRequest;
import com.cloudaat.agriml.crop.api.CropYieldResponse;
import com.cloudaat.agriml.crop.service.CropYieldModelService;
import com.cloudaat.agriml.record.model.CropYieldRecord;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CropRecordService {

    private final CropYieldModelService cropYieldModelService;
    private final Map<Long, CropYieldRecord> store = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public CropRecordService(CropYieldModelService cropYieldModelService) {
        this.cropYieldModelService = cropYieldModelService;
    }

    public CropYieldRecord create(CropYieldRequest request) {
        CropYieldResponse response = cropYieldModelService.predictYield(request);
        CropYieldRecord record = new CropYieldRecord();
        record.setId(nextId.getAndIncrement());
        record.setCropType(request.getCropType());
        record.setAreaHectares(request.getAreaHectares());
        record.setSeasonalRainfallMm(request.getSeasonalRainfallMm());
        record.setNitrogenFertilizerKgPerHa(request.getNitrogenFertilizerKgPerHa());
        record.setSoilOrganicCarbonPct(request.getSoilOrganicCarbonPct());
        record.setAvgTemperatureC(request.getAvgTemperatureC());
        record.setHistoricalYieldTonPerHa(request.getHistoricalYieldTonPerHa());
        record.setPredictedYieldTonPerHa(response.getPredictedYieldTonPerHa());
        record.setTotalProductionTons(response.getTotalProductionTons());
        record.setExplanation(response.getExplanation());
        record.setCreatedAt(LocalDateTime.now());
        store.put(record.getId(), record);
        return record;
    }

    public List<CropYieldRecord> findAll() {
        return new ArrayList<>(store.values());
    }

    public CropYieldRecord findById(Long id) {
        return store.get(id);
    }

    public CropYieldRecord update(Long id, CropYieldRequest request) {
        CropYieldRecord existing = store.get(id);
        if (existing == null) {
            return null;
        }
        CropYieldResponse response = cropYieldModelService.predictYield(request);
        existing.setCropType(request.getCropType());
        existing.setAreaHectares(request.getAreaHectares());
        existing.setSeasonalRainfallMm(request.getSeasonalRainfallMm());
        existing.setNitrogenFertilizerKgPerHa(request.getNitrogenFertilizerKgPerHa());
        existing.setSoilOrganicCarbonPct(request.getSoilOrganicCarbonPct());
        existing.setAvgTemperatureC(request.getAvgTemperatureC());
        existing.setHistoricalYieldTonPerHa(request.getHistoricalYieldTonPerHa());
        existing.setPredictedYieldTonPerHa(response.getPredictedYieldTonPerHa());
        existing.setTotalProductionTons(response.getTotalProductionTons());
        existing.setExplanation(response.getExplanation());
        return existing;
    }

    public boolean delete(Long id) {
        return store.remove(id) != null;
    }
}
