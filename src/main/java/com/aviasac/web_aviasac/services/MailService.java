package com.aviasac.web_aviasac.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.resend.*;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.*;

@Service
public class MailService {

    private final Resend resend;

    public MailService(@Value("${resend.api.key}") String resendApiKey) {
        System.out.println("🔹 RESEND KEY CARGADA: " + resendApiKey.substring(0, 8) + "...");
        this.resend = new Resend(resendApiKey);
    }

    public void enviarCorreo(String destino, String asunto, String html) throws Exception {

        System.out.println("📧 Enviando correo...");
        System.out.println("➡ Destino: " + destino);
        System.out.println("➡ Asunto: " + asunto);

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("Web Aviasac <onboarding@resend.dev>") // TEMPORAL
                .to(destino)
                .subject(asunto)
                .html(html)
                .build();

        try {
            var response = resend.emails().send(params);
            System.out.println("✅ Correo enviado. ID: " + response.getId());
        } catch (ResendException e) {
            System.out.println("❌ ERROR AL ENVIAR EL CORREO:");
            e.printStackTrace();
            throw new Exception("Error enviando correo con Resend", e);
        }
    }
}
