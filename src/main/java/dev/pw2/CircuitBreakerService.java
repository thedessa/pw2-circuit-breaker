package dev.pw2;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicLong;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;

@ApplicationScoped
public class CircuitBreakerService {

    private AtomicLong counter = new AtomicLong(0);

    private final SecureRandom random = new SecureRandom();

    @CircuitBreaker(requestVolumeThreshold = 4)
    public Integer getAvailability() {
        maybeFail();
        return random.nextInt(30);
    }

    private void maybeFail() {
        final Long invocationNumber = counter.getAndIncrement();
        if (invocationNumber % 4 > 1) { // alternate 2 successful and 2 failing invocations
            throw new RuntimeException("Service failed.");
        }
    }
}
