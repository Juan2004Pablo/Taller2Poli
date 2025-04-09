package com.example.demo_2.Models.DTOs;

import lombok.Data;

@Data
public class RegisterRequest {
    private Long identification;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String password;
}