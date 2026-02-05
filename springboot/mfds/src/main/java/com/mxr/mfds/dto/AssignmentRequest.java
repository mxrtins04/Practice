package com.mxr.mfds.dto;

public class AssignmentRequest {
    private Long attendantId;
    private Long dispenserId;

    public Long getAttendantId() {
        return attendantId;
    }

    public void setAttendantId(Long attendantId) {
        this.attendantId = attendantId;
    }

    public Long getDispenserId() {
        return dispenserId;
    }

    public void setDispenserId(Long dispenserId) {
        this.dispenserId = dispenserId;
    }
}
