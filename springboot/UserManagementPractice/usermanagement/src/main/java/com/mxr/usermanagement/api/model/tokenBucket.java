package com.mxr.usermanagement.api.model;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;




public class tokenBucket {
    private AtomicInteger numberOfTokens = new AtomicInteger(1);
    private Instant startTime = Instant.now();

    public boolean isAllowed(int maxRequests, Duration timePeriod){
        Duration timeElapsed = Duration.between(startTime, Instant.now());
        if( timeElapsed.compareTo(timePeriod) > 0 ){
            numberOfTokens.set(1);
            startTime = Instant.now();

            return true;
        }

        numberOfTokens.incrementAndGet();
        if (numberOfTokens > maxRequests) return false;
    }


}
