package com.example.demo_2.Services;

import com.example.demo_2.Config.JwtService;
import com.example.demo_2.Models.DTOs.AuthRequest;
import com.example.demo_2.Models.DTOs.AuthResponse;
import com.example.demo_2.Models.DTOs.RegisterRequest;
import com.example.demo_2.Models.Entities.User;
import com.example.demo_2.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        try {
            // Verificar si el usuario ya existe
            if (repository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("El email ya está registrado");
            }
            
            // Crear usuario
            var user = new User();
            user.setIdentification(request.getIdentification());
            user.setName(request.getName());
            user.setAddress(request.getAddress());
            user.setPhone(request.getPhone());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole("USER");
            user.setCreatedAt(LocalDateTime.now());
            
            // Guardar usuario
            User savedUser = repository.save(user);
            System.out.println("Usuario guardado con ID: " + savedUser.getIdentification());
            
            // Generar token JWT
            var jwtToken = jwtService.generateToken(user);
            return AuthResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (Exception e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional
    public AuthResponse authenticate(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = repository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            var jwtToken = jwtService.generateToken(user);
            return AuthResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (Exception e) {
            System.err.println("Error al autenticar usuario: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}