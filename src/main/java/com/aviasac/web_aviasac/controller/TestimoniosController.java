package com.aviasac.web_aviasac.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aviasac.web_aviasac.model.PreguntaFrecuente;

@Controller
public class TestimoniosController {

    private List<PreguntaFrecuente> preguntaFrecuentes = new ArrayList<>(Arrays.asList(
            new PreguntaFrecuente(
                    1,
                    "¿Cuántas hectáreas cubre el servicio con drone?",
                    "Los drones cubren áreas pequeñas e irregulares, generalmente hasta 20 hectáreas por jornada.",
                    true),
            new PreguntaFrecuente(
                    2,
                    "¿Cuál es el mínimo para fumigación con avioneta?",
                    "El servicio con avioneta requiere un mínimo de 10 hectáreas para garantizar la eficiencia del vuelo.",
                    true),
            new PreguntaFrecuente(
                    3,
                    "¿Entregan reportes de aplicación?",
                    "Sí, entregamos reportes digitales con detalles de hectáreas tratadas, tipo de producto recomendaciones técnicas.",
                    true)));


    @GetMapping("/testimonios")
    public String testimonios(Model model) {
        model.addAttribute("preguntasFrecuentes", preguntaFrecuentes);
        return "testimonios";
    }


    @PostMapping("/newfaq")
    @ResponseBody
    public PreguntaFrecuente newPreguntaFrecuente(@RequestBody PreguntaFrecuente newfaq){
        preguntaFrecuentes.add(newfaq);
        return newfaq;
    }
}
