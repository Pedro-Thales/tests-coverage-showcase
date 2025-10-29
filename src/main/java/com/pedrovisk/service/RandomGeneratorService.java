
package com.pedrovisk.service;

import com.pedrovisk.api.RandomApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RandomGeneratorService {

    private final RandomApi randomApi;

    public String generateRandomString() {
        log.info("Generating random string");
        try {
            var response = randomApi.generateRandomString();
            log.debug("Random string generated: {}", response);
            return response;
        } catch (Exception e) {
            log.error("Error generating random string", e);
            return null;
        }
    }
}
