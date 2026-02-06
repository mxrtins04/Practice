package com.mxr.to_do.dtos;

import com.mxr.to_do.enums.Priority;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        Priority priority,
        Boolean isComplete,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
