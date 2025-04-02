// AuthRequest.java
package com.example.demo_2.Models.DTOs;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
