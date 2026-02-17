package com.mxr.usermanagement.data.dto.requests;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;



@NoArgsConstructor
@AllArgsConstructor
@Data
public class response {
    private long id;
    private String message;
    private String name;
    private String email;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
     
}
