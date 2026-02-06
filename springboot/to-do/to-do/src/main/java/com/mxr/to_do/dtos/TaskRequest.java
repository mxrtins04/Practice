package com.mxr.to_do.dtos;

import com.mxr.to_do.enums.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskRequest {
    
    @NotBlank(message = "Title is required")
    private String title;
    
    private String description;
    
    @NotNull(message = "Priority is required")
    private Priority priority;
    
    private Boolean isComplete = false;
}
