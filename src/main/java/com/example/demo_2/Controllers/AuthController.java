package com.example.demo_2.Controllers;

import com.example.demo_2.Models.DTOs.AuthRequest;
import com.example.demo_2.Models.DTOs.AuthResponse;
import com.example.demo_2.Models.DTOs.RegisterRequest;
import com.example.demo_2.Services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequest request) {
        service.register(request);
        return "auth/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("authRequest", new AuthRequest());
        return "auth/login";
    }

    @PostMapping("/login")
    public String authenticate(@ModelAttribute AuthRequest request) {
        service.authenticate(request);
        return "redirect:/";
    }

    // API endpoints for programmatic access (e.g., mobile apps)
    @PostMapping("/register/api")
    public ResponseEntity<AuthResponse> registerApi(@ModelAttribute RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate/api")
    public ResponseEntity<AuthResponse> authenticateApi(@ModelAttribute AuthRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}