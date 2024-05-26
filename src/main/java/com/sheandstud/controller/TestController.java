package com.sheandstud.controller;

import com.sheandstud.model.Patient;
import com.sheandstud.model.Test;
import com.sheandstud.service.PatientService;
import com.sheandstud.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tests")
public class TestController {
    private final TestService testService;
    private final PatientService patientService;


    @Autowired
    public TestController(TestService testService, PatientService patientService) {
        this.testService = testService;
        this.patientService = patientService;
    }

    @PostMapping("/submit")
    public void submitTest(@RequestParam Long patientId, @RequestParam Long testId, @RequestParam int score) {
        // Здесь предполагается, что у вас есть сервисы для получения пациента и теста по их id
        Patient patient = patientService.getPatientById(patientId);
        Test test = testService.getTestById(testId);
        testService.submitTest(patient, test, score);
    }
}
