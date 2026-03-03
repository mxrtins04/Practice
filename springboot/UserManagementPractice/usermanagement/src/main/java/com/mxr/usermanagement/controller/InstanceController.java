package com.mxr.usermanagement.controller;

import com.mxr.usermanagement.config.InstanceConfig;
import com.mxr.usermanagement.data.dto.InstanceInfoResponse;

import java.lang.management.ManagementFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class InstanceController {
    private final InstanceConfig config;

    public InstanceController(InstanceConfig config) {
        this.config = config;
    }

    @GetMapping("/api/instance/info")
    public ResponseEntity<InstanceInfoResponse> getInstanceInfo() {
        float uptimeSeconds = ManagementFactory.getRuntimeMXBean().getUptime() / 1000.0f;
        InstanceInfoResponse response = InstanceInfoResponse.builder()
                .instanceId(config.getInstanceId())
                .region(config.getRegion())
                .uptimeSeconds(uptimeSeconds)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
