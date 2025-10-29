package com.pedrovisk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CalculatorChallengeApiApplicationTest {

    @Test
    void mainMethodRunsWithoutExceptions() {
        assertDoesNotThrow(() ->
                CalculatorChallengeApiApplication.main(new String[] {})
        );
    }
}