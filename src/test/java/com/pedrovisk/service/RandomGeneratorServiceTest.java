
package com.pedrovisk.service;

import com.pedrovisk.api.RandomApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RandomGeneratorServiceTest {

    private RandomGeneratorService randomGeneratorService;

    @Mock
    private RandomApi randomApi;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        randomGeneratorService = new RandomGeneratorService(randomApi);
    }

    @Test
    void testGenerateRandomString() {

        when(randomApi.generateRandomString()).thenReturn("random123");
        
        String result = randomGeneratorService.generateRandomString();
        
        assertNotNull(result);
        verify(randomApi).generateRandomString();
    }

    @Test
    void testGenerateRandomString_Exception_MissesBug() {

        when(randomApi.generateRandomString()).thenThrow(new RuntimeException("API Error"));
        String result = randomGeneratorService.generateRandomString();

        assertNull(result);
    }

}
