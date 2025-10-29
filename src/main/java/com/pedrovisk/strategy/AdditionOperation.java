
package com.pedrovisk.strategy;

import com.pedrovisk.model.dto.CalculatorRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdditionOperation implements OperationStrategy {
    @Override
    public String execute(CalculatorRequest request) {
        log.info("Executing addition operation");
        float result = request.getFirstValue() + request.getSecondValue();
        return String.valueOf(result);
    }
}
