package com.sheandstud.controller;

import com.sheandstud.model.HealthData;
import com.sheandstud.model.HealthDataWithEmail;
import com.sheandstud.model.Patient;
import com.sheandstud.service.ActivityService;
import com.sheandstud.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthDataController {

    private final PatientService patientService;
    private final ActivityService activityService;

    @Autowired
    public HealthDataController(PatientService patientService, ActivityService activityService) {
        this.patientService = patientService;
        this.activityService = activityService;
    }

    @PostMapping("/healthdata")
    public ResponseEntity<String> receiveHealthData(@RequestBody HealthDataWithEmail healthDataWithEmail) {
        // Получаем почту и данные о здоровье из запроса
        String email = healthDataWithEmail.getEmail();
        HealthData healthData = healthDataWithEmail.getHealthData();
        // Ищем пациента по электронной почте
        Patient patient = patientService.findPatientByEmail(email);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }
        // Сохраняем полученные данные в таблице activity, связанной с пациентом
        activityService.saveActivityForPatient(patient, healthData);
        // Возвращаем ответ с подтверждением получения данных
        return ResponseEntity.ok("Health data with email received and saved successfully");
    }
}