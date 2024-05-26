package com.sheandstud.controller;
import com.sheandstud.model.Doctor;
import com.sheandstud.model.Patient;
import com.sheandstud.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }



    @GetMapping("/patient")
    public String patientPage() {
        return "patient"; // Имя вашего шаблона Thymeleaf для страницы пациента
    }

    @GetMapping("/patient/{id}")
    public String showPatientPage(@PathVariable("id") Long id, Model model) {
        Patient patient = patientService.findPatientById(id);
        if (patient != null) {
            model.addAttribute("patient", patient);
            return "patient"; // Имя вашего шаблона Thymeleaf
        } else {
            // Обработка ситуации, когда пациент не найден
            return "error"; // Имя страницы ошибки или редирект
        }
    }
}
