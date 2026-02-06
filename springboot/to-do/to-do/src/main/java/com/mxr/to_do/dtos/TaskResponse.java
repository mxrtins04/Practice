package com.mxr.to_do.dtos;

import com.mxr.to_do.enums.Priority;



public record TaskResponse(
        Long id,
        String title,
        String description,
        Priority priority,
        Boolean isComplete
) {
}
