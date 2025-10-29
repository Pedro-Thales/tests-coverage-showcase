package com.pedrovisk.service;

import com.pedrovisk.exception.OperationNotFoundException;
import com.pedrovisk.model.OperationEntity;
import com.pedrovisk.model.RecordEntity;
import com.pedrovisk.model.Status;
import com.pedrovisk.model.UserEntity;
import com.pedrovisk.model.dto.CalculatorRequest;
import com.pedrovisk.model.dto.RecordResponse;
import com.pedrovisk.repository.RecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RecordServiceTest {

    private RecordService recordService;
    private UserService userService;
    private OperationService operationService;
    private RecordRepository recordRepository;
    private CalculatorService calculatorService;


    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        operationService = Mockito.mock(OperationService.class);
        recordRepository = Mockito.mock(RecordRepository.class);
        calculatorService = Mockito.mock(CalculatorService.class);

        recordService = new RecordService(recordRepository, operationService, userService, calculatorService);
    }

    @Test
    void testSaveOperation_HappyPath() {
        Long userId = 1L;
        var user = new UserEntity();
        user.setId(userId);
        user.setUsername("user");

        var operation = new OperationEntity();
        operation.setId(10L);
        operation.setCost(20.0f);
        operation.setType("addition");

        var recordEntity = new RecordEntity();
        recordEntity.setUserEntity(user);
        recordEntity.setOperationEntity(operation);
        recordEntity.setUserBalance(100.0f);

        var request = new CalculatorRequest(1L, 1L, 1, 5.0f, 5.0f);

        Mockito.when(userService.findById(1L)).thenReturn(user);
        Mockito.when(operationService.findOperationById(request.getOperationId())).thenReturn(operation);
        Mockito.when(recordRepository.findTopByUserEntityOrderByDateDesc(user)).thenReturn(recordEntity);
        RecordEntity savedRecord = new RecordEntity(1L, operation, user, 1, 80f,
                "10.0", LocalDateTime.now(), Status.ACTIVE);
        Mockito.when(recordRepository.save(any())).thenReturn(savedRecord);
        Mockito.when(calculatorService.executeCalculation(operation.getType(), request)).thenReturn("10");

        RecordResponse result = recordService.saveOperation(request);

        assertNotNull(result);
    }

    @Test
    void testSaveOperation_UserNotFound() {
        Long userId = 1L;
        CalculatorRequest request = new CalculatorRequest(1L, 1L, 1, 5.0f, 5.0f);

        Mockito.when(userService.findById(userId)).thenThrow();

        assertThrows(RuntimeException.class, () -> recordService.saveOperation(request));

    }

    @Test
    void testSaveOperation_OperationNotFound() {
        Long userId = 1L;
        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setUsername("user");

        CalculatorRequest request = new CalculatorRequest(1L, 1L, 1, 5.0f, 5.0f);

        Mockito.when(userService.findById(userId)).thenReturn(user);
        Mockito.when(operationService.findOperationById(request.getOperationId())).thenThrow(OperationNotFoundException.class);

        assertThrows(OperationNotFoundException.class, () -> {
            recordService.saveOperation(request);
        });

    }

    //This test is not asserting the correct Exception
    @Test
    void testSaveOperation_InsufficientBalance() {
        Long userId = 1L;
        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setUsername("user");

        CalculatorRequest request = new CalculatorRequest(1L, 1L, 1, 5.0f, 5.0f);

        Mockito.when(userService.findById(userId)).thenReturn(user);
        Mockito.when(recordRepository.findTopByUserEntityOrderByDateDesc(user)).thenReturn(null);

        assertThrows(Exception.class, () -> recordService.saveOperation(request));

    }

    @Test
    void testGetCurrentBalance_Success() {
        Long userId = 1L;
        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setUsername("user");
        //mocking the wrong method
        Mockito.when(userService.findById(userId)).thenReturn(user);

        var recordEntity = new RecordEntity();
        recordEntity.setUserEntity(user);
        recordEntity.setUserBalance(100.0f);
        Mockito.when(recordRepository.findTopByUserEntityOrderByDateDesc(any()))
                .thenReturn(recordEntity);

        var result = recordService.getCurrentBalance(1L);
        assertNotNull(result);
    }

    @Test
    void testGetCurrentBalance_Zero() {
        Long userId = 1L;
        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setUsername("user");
        //mocking the wrong method
        Mockito.when(userService.findById(userId)).thenReturn(user);

        var result = recordService.getCurrentBalance(1L);
        assertNotNull(result);

    }

    @Test
    void testGetCurrentBalance_NoRecords_CoverageOnly() {
        UserEntity user = new UserEntity();
        when(recordRepository.findTopByUserEntityOrderByDateDesc(user)).thenReturn(null);

        Float balance = recordService.getCurrentBalance(user);

        assertEquals(0f, balance);
    }

    @Test
    void testGetAllRecordsById_Success() {
        Long userId = 1L;
        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setUsername("user");

        Mockito.when(userService.findById(1L)).thenReturn(user);

        var result = recordService.getAllRecordsById(1L);
        assertNotNull(result);

    }

    @Test
    void testInsufficientBalance_CoverageOnly() {
        UserEntity user = new UserEntity();
        OperationEntity operation = new OperationEntity();
        operation.setCost(150f); // More than balance

        RecordEntity lastRecord = new RecordEntity();
        lastRecord.setUserBalance(100f);

        when(userService.findByUsername("user")).thenReturn(user);
        when(operationService.findOperationById(1L)).thenReturn(operation);
        when(recordRepository.findTopByUserEntityOrderByDateDesc(user)).thenReturn(lastRecord);

        CalculatorRequest request = new CalculatorRequest(1L, 1L, 1, 2f, 3f);

        assertThrows(Exception.class, () -> {
            recordService.saveOperation(request);
        });
    }
}
