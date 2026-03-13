package com.cloudaat.agriml.record.web;

import com.cloudaat.agriml.crop.api.CropYieldRequest;
import com.cloudaat.agriml.record.model.CropYieldRecord;
import com.cloudaat.agriml.record.service.CropRecordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CRUD REST API for crop yield records.
 * Use with Postman: Create, Read, Update, Delete.
 */
@RestController
@RequestMapping("/api/records")
public class CropRecordController {

    private final CropRecordService cropRecordService;

    public CropRecordController(CropRecordService cropRecordService) {
        this.cropRecordService = cropRecordService;
    }

    @PostMapping
    public ResponseEntity<CropYieldRecord> create(@Valid @RequestBody CropYieldRequest request) {
        CropYieldRecord record = cropRecordService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(record);
    }

    @GetMapping
    public ResponseEntity<List<CropYieldRecord>> getAll() {
        List<CropYieldRecord> records = cropRecordService.findAll();
        return ResponseEntity.ok(records);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CropYieldRecord> getById(@PathVariable Long id) {
        CropYieldRecord record = cropRecordService.findById(id);
        if (record == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(record);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CropYieldRecord> update(@PathVariable Long id,
                                                  @Valid @RequestBody CropYieldRequest request) {
        CropYieldRecord record = cropRecordService.update(id, request);
        if (record == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(record);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = cropRecordService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
