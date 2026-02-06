package com.mxr.to_do.dtos;

import com.mxr.to_do.enums.Priority;

import java.time.LocalDateTime;

public record CompletedTaskResponse(
        Long id,
        String title,
        String description,
        Priority priority,
        Long originalTaskId,
        LocalDateTime completedAt
) {
}
