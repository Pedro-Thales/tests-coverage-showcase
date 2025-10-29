package com.pedrovisk.service;

import com.pedrovisk.model.dto.CalculatorRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CalculatorServiceTest {

    private CalculatorService calculatorService;

    @Mock
    RandomGeneratorService randomGeneratorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        calculatorService = new CalculatorService(randomGeneratorService);
    }

    @Test
    void testAddition() {
        CalculatorRequest request = new CalculatorRequest(1L, 1L, 1, 1f, 2f);
        var result = calculatorService.executeCalculation("addition", request);
        assertEquals("3.0", result);
    }

    @Test
    void testAddition_WithNegativeNumbers() {
        CalculatorRequest request = new CalculatorRequest(1L, 1L, 1, -3f, -2f);
        var result = calculatorService.executeCalculation("addition", request);
        assertEquals("-5.0", result);
    }

    @Test
    void testAddition_WithLargeNumbers() {
        CalculatorRequest request = new CalculatorRequest(1L, 1L, 1, Float.MAX_VALUE, 1f);
        var result = calculatorService.executeCalculation("addition", request);
        assertEquals("3.4028235E38", result);
    }

    @Test
    void testSubtraction() {
        CalculatorRequest request = new CalculatorRequest(1L, 1L, 1, 1f, 2f);
        var result = calculatorService.executeCalculation("subtraction", request);
        assertEquals("-1.0", result);
    }

    @Test
    void testSubtraction_NegativeResult() {
        CalculatorRequest request = new CalculatorRequest(1L, 1L, 1, -1f, -5f);
        var result = calculatorService.executeCalculation("subtraction", request);
        assertEquals("4.0", result);
    }

    @Test
    void testMultiplication() {
        CalculatorRequest request = new CalculatorRequest(1L, 1L, 1, 10.0f, 2.0f);
        var result = calculatorService.executeCalculation("multiplication", request);
        assertEquals("20.0", result);
    }

    @Test
    void testMultiplication_WithNegativeNumber() {
        CalculatorRequest request = new CalculatorRequest(1L, 1L, 1, -3f, 4f);
        var result = calculatorService.executeCalculation("multiplication", request);
        assertEquals("-12.0", result);
    }

    @Test
    void testDivision() {
        CalculatorRequest request = new CalculatorRequest(1L, 1L, 1, 1.0f, 2.0f);
        var result = calculatorService.executeCalculation("division", request);
        assertEquals("0.5", result);
    }

    @Test
    void testDivision_ByZero() {
        CalculatorRequest request = new CalculatorRequest(1L, 1L, 1, 5f, 0f);
        assertThrows(ArithmeticException.class, () -> {
            calculatorService.executeCalculation("division", request);
        });
    }

    @Test
    void testDivision_WithNegativeDividend() {
        CalculatorRequest request = new CalculatorRequest(1L, 1L, 1, -6f, 2f);
        var result = calculatorService.executeCalculation("division", request);
        assertEquals("-3.0", result);
    }

    @Test
    void testDivision_WithNegativeDivisor() {
        CalculatorRequest request = new CalculatorRequest(1L, 1L, 1, 6f, -2f);
        var result = calculatorService.executeCalculation("division", request);
        assertEquals("-3.0", result);
    }

    @Test
    void testSquareRoot() {
        CalculatorRequest request = new CalculatorRequest(1L, 1L, 1, 9.0f, 2.0f);
        var result = calculatorService.executeCalculation("square_root", request);
        assertEquals("3.0", result);
    }

    @Test
    void testSquareRoot_WithNegativeNumber() {
        CalculatorRequest request = new CalculatorRequest(1L, 1L, 1, -9f, 0f);
        assertThrows(ArithmeticException.class, () -> {
            calculatorService.executeCalculation("square_root", request);
        });
    }

    @Test
    void testRandomString() {
        when(randomGeneratorService.generateRandomString()).thenReturn("test");
        CalculatorRequest request = new CalculatorRequest(1L, 1L, 1, 0f, 0f);
        var result = calculatorService.executeCalculation("random_string", request);
        assertNotNull(result);     }

    @Test
    void testUnsupportedOperation_CoverageOnly() {
        CalculatorRequest request = new CalculatorRequest(1L, 1L, 1, 1f, 1f);
        assertThrows(Exception.class, () -> {
            calculatorService.executeCalculation("invalid", request);
        });
    }


}
