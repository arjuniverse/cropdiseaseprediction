package com.cloudaat.agriml.crop.web;

import com.cloudaat.agriml.crop.api.CropYieldRequest;
import com.cloudaat.agriml.crop.api.CropYieldResponse;
import com.cloudaat.agriml.crop.service.CropYieldModelService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/crops")
public class CropController {

    private final CropYieldModelService cropYieldModelService;

    public CropController(CropYieldModelService cropYieldModelService) {
        this.cropYieldModelService = cropYieldModelService;
    }

    @PostMapping("/predict-yield")
    public ResponseEntity<CropYieldResponse> predictYield(@Valid @RequestBody CropYieldRequest request) {
        CropYieldResponse response = cropYieldModelService.predictYield(request);
        return ResponseEntity.ok(response);
    }
}
