package com.example.demo_2.Api.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class QuerySessionResponse {

    @JsonProperty("status")
    private Status status;

    public Status getStatus() {
        return status;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Status {
        @JsonProperty("status")
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
