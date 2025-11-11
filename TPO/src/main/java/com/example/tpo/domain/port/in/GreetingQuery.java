package com.example.tpo.domain.port.in;

import com.example.tpo.domain.model.Greeting;

/**
 * Inbound port that defines how the application can be queried for greeting messages.
 */
public interface GreetingQuery {

    /**
     * Resolves a greeting message for the provided name.
     *
     * @param name person to greet
     * @return a domain {@link Greeting}
     */
    Greeting getGreeting(String name);
}
