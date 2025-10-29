package com.pedrovisk.service;

import com.pedrovisk.model.OperationEntity;
import com.pedrovisk.repository.OperationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class OperationServiceTest {

    @Test
    void findOperationById_shouldReturnCorrect() {

        OperationRepository operationRepository = Mockito.mock(OperationRepository.class);
        OperationService operationService = new OperationService(operationRepository);
        when(operationRepository.findById(1L)).thenReturn(
                Optional.of(new OperationEntity(1L, "", 1f)));

        var result = operationService.findOperationById(1L);

        assertNotNull(result);

    }

    @Test
    void getAllOperations_shouldReturnCorrect() {
        OperationRepository operationRepository = Mockito.mock(OperationRepository.class);
        OperationService operationService = new OperationService(operationRepository);
        when(operationRepository.findAll()).thenReturn(
                List.of(new OperationEntity(1L, "", 1f)));
        var result = operationService.getAllOperations();
        assertNotNull(result);
    }



}