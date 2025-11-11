package com.example.tpo.application.service;

import com.example.tpo.domain.model.Greeting;
import com.example.tpo.domain.port.in.GreetingQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Service;

/**
 * Application service that implements the {@link GreetingQuery} use case.
 */
@Service
public class GreetingService implements GreetingQuery {

    private static final Logger LOGGER = LogManager.getLogger(GreetingService.class);

    @Override
    public Greeting getGreeting(String name) {
        String requestId = ThreadContext.get("requestId");
        LOGGER.info("event=greeting_service status=START requestId={} name={}", requestId, name);

        String recipient = (name == null || name.isBlank()) ? "Mundo" : name.trim();
        Greeting greeting = new Greeting("Hola, " + recipient + "!");

        LOGGER.info("event=greeting_service status=END requestId={}", requestId);
        return greeting;
    }
}
