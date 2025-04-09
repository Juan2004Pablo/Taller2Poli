package com.example.demo_2.Api.Response;

public class CreateSessionResponse {
    private StatusResponse status;
    private Long requestId;
    private String processUrl;

    public StatusResponse getStatus() {
        return status;
    }
    public void setStatus(StatusResponse status) {
        this.status = status;
    }
    public Long getRequestId() {
        return requestId;
    }
    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }
    public String getProcessUrl() {
        return processUrl;
    }
    public void setProcessUrl(String processUrl) {
        this.processUrl = processUrl;
    }
}
