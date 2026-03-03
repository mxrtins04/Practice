package com.mxr.usermanagement.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor


public class InstanceInfoResponse {
    private String instanceId;
    private String region;
    private final String status = "UP";
    private float uptimeSeconds;
    private final String serviceName = "devarena-user-service";

}
