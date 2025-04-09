package com.example.demo_2.Services.Api;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;

import java.security.MessageDigest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo_2.Api.Amount;
import com.example.demo_2.Api.Auth;
import com.example.demo_2.Api.Payment;
import com.example.demo_2.Api.Request.CreateSessionRequest;
import com.example.demo_2.Api.Response.CreateSessionResponse;
import com.example.demo_2.Models.Entities.Order;
import com.example.demo_2.Models.Entities.Pay;

@Service
public class PlaceToPayService {

    @Autowired
    private RestTemplate restTemplate;

    // URL del endpoint (ajusta de acuerdo a ambiente, por ejemplo sandbox o
    // producción)
    private final String URL_CREATE_SESSION = "https://checkout-test.placetopay.com/api/session";

    // Tus credenciales proporcionadas
    private final String LOGIN = "8bb41265c7b1e5204aebfc7a8b0c489f";
    private final String TRAN_KEY = "nkgkBE7cw44juJQT";

    @Transactional
    public CreateSessionResponse createSession(Order order) throws Exception {
        try {
            Pay pay = order.getPay();

            Auth auth = buildAuth();
            Amount amount = new Amount(order.getTotal());
            Payment payment = new Payment(pay.getReference(), "Transacción de pago", amount);

            // 2. Construir el request de sesión
            CreateSessionRequest sessionRequest = new CreateSessionRequest();
            sessionRequest.setAuth(auth);
            sessionRequest.setPayment(payment);
            sessionRequest.setExpiration(OffsetDateTime.now().plusMinutes(30).toString()); // Válida 30 minutos
            sessionRequest.setReturnUrl("http://localhost:8065/"); // URL de retorno luego del pago
            sessionRequest.setIpAddress("127.0.0.1"); // Usa la IP real del cliente si es necesario
            sessionRequest.setUserAgent("Spring Boot App"); // O el user agent real

            // 3. Configurar headers y enviar la solicitud
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<CreateSessionRequest> requestEntity = new HttpEntity<>(sessionRequest, headers);

            ResponseEntity<CreateSessionResponse> responseEntity = restTemplate.postForEntity(URL_CREATE_SESSION,
                    requestEntity, CreateSessionResponse.class);

            CreateSessionResponse response = responseEntity.getBody();

            if (response == null || response.getStatus() == null || !"OK".equals(response.getStatus().getStatus())) {
                throw new RuntimeException("Error en la respuesta de PlaceToPay: " + responseEntity);
            }

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear la sesión con PlaceToPay: " + e.getMessage());
        }
    }

    public Auth buildAuth() throws NoSuchAlgorithmException {
        String seed = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        // Generar un UUID como nonce crudo
        String rawNonce = UUID.randomUUID().toString();
        String nonceBase64 = Base64.getEncoder().encodeToString(rawNonce.getBytes(StandardCharsets.UTF_8));

        // Generar el tranKey con hash SHA256 del rawNonce + seed + secretKey, luego
        // codificado en base64
        String plainText = rawNonce + seed + TRAN_KEY;

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] sha256 = md.digest(plainText.getBytes(StandardCharsets.UTF_8));
        String tranKey = Base64.getEncoder().encodeToString(sha256);

        Auth auth = new Auth();
        auth.setLogin(LOGIN);
        auth.setSeed(seed);
        auth.setNonce(nonceBase64);
        auth.setTranKey(tranKey);

        return auth;
    }
}
