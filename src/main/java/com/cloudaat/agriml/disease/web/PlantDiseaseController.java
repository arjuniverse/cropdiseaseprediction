package com.cloudaat.agriml.disease.web;

import com.cloudaat.agriml.disease.api.PlantDiseaseRequest;
import com.cloudaat.agriml.disease.api.PlantDiseaseResponse;
import com.cloudaat.agriml.disease.service.PlantDiseaseModelService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plants")
public class PlantDiseaseController {

    private final PlantDiseaseModelService plantDiseaseModelService;

    public PlantDiseaseController(PlantDiseaseModelService plantDiseaseModelService) {
        this.plantDiseaseModelService = plantDiseaseModelService;
    }

    @PostMapping("/detect-disease")
    public ResponseEntity<PlantDiseaseResponse> detectDisease(@Valid @RequestBody PlantDiseaseRequest request) {
        PlantDiseaseResponse response = plantDiseaseModelService.detectDisease(request);
        return ResponseEntity.ok(response);
    }
}
