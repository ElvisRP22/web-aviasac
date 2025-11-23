package com.aviasac.web_aviasac.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aviasac.web_aviasac.services.MailService;

@Controller
public class ContactanosController {

    @GetMapping("/contactanos")
    public String contactanos() {
        return "contactanos";
    }

    private final MailService emailService;

    public ContactanosController(MailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/contacto/enviar")
    @ResponseBody
    public Map<String, Object> enviarFormulario(
            @RequestParam String ruc,
            @RequestParam String razonSocial,
            @RequestParam String representante,
            @RequestParam String email,
            @RequestParam String telefono,
            @RequestParam String asunto,
            @RequestParam String mensaje) {

        System.out.println("Recibiendo datos del formulario:");
        System.out.println("➡ RUC: " + ruc);
        System.out.println("➡ Razón Social: " + razonSocial);
        System.out.println("➡ Email: " + email);

        String contenidoHtml = """
        <div style="font-family: Arial, Helvetica, sans-serif; background-color: #f5f7fa; padding: 20px;">
            <table align="center" width="600" style="background: white; border-radius: 10px; padding: 20px; border: 1px solid #e6e6e6;">
                <tr>
                    <td style="text-align: center; padding-bottom: 20px;">
                        <h2 style="color: #0d6efd; margin: 0;">📩 Nueva Consulta desde el Formulario</h2>
                        <p style="color: #666; margin-top: 5px;">Has recibido un nuevo mensaje desde la web.</p>
                    </td>
                </tr>

                <tr>
                    <td>
                        <table width="100%%" style="font-size: 15px; color: #333;">
                            <tr>
                                <td style="padding: 8px 0;"><strong>RUC:</strong></td>
                                <td style="padding: 8px 0;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding: 8px 0;"><strong>Razón Social:</strong></td>
                                <td style="padding: 8px 0;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding: 8px 0;"><strong>Representante:</strong></td>
                                <td style="padding: 8px 0;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding: 8px 0;"><strong>Correo:</strong></td>
                                <td style="padding: 8px 0;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding: 8px 0;"><strong>Teléfono:</strong></td>
                                <td style="padding: 8px 0;">%s</td>
                            </tr>
                        </table>
                    </td>
                </tr>

                <tr>
                    <td style="padding-top: 20px;">
                        <h3 style="color: #0d6efd;">📨 Mensaje:</h3>
                        <div style="background: #f0f4ff; padding: 15px; border-radius: 8px; white-space: pre-line; color: #333;">
                            %s
                        </div>
                    </td>
                </tr>

                <tr>
                    <td style="text-align: center; padding-top: 30px; color: #888; font-size: 13px;">
                        <p>Este mensaje fue enviado desde el formulario de contacto de AVIASAC.</p>
                    </td>
                </tr>
            </table>
        </div>
        """.formatted(ruc, razonSocial, representante, email, telefono, mensaje);


        try {
            emailService.enviarCorreo(
                    "eremaycunarp544@gmail.com",
                    asunto,
                    contenidoHtml);

            return Map.of(
                    "success", true,
                    "message", "Mensaje enviado correctamente");

        } catch (Exception e) {
            System.out.println("Error en el controller:");
            e.printStackTrace();
            return Map.of(
                    "success", false,
                    "message", "Error al enviar el mensaje");
        }
    }

}
