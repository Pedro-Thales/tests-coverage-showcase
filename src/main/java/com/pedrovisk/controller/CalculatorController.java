package com.pedrovisk.controller;

import com.pedrovisk.model.OperationEntity;
import com.pedrovisk.model.dto.CalculatorRequest;
import com.pedrovisk.model.dto.RecordResponse;
import com.pedrovisk.service.OperationService;
import com.pedrovisk.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/calculator")
@CrossOrigin(origins = {"http://localhost", "http://localhost:3000", "https://calculator-challenge.pedrovisk.com/"}, allowedHeaders = "*")
@RequiredArgsConstructor
public class CalculatorController {

    private final OperationService operationService;
    private final RecordService recordService;


    @GetMapping("/balance/{userId}")
    public Float getUserBalance(@PathVariable Long userId) {
        return recordService.getCurrentBalance(userId);
    }

    @PostMapping("/calculate")
    public RecordResponse executeOperation(@RequestBody CalculatorRequest calculatorRequest) {
        return recordService.saveOperation(calculatorRequest);
    }

    @GetMapping("/operations")
    public List<OperationEntity> getOperations() {
        return operationService.getAllOperations();
    }

    @GetMapping("/records/{userId}")
    public List<RecordResponse> getRecordsByUsername(@PathVariable Long userId) {
        return recordService.getAllRecordsById(userId);
    }

}
