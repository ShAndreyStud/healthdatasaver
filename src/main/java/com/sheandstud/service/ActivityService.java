package com.sheandstud.service;

import com.sheandstud.model.Activity;
import com.sheandstud.model.HealthData;
import com.sheandstud.model.Patient;
import com.sheandstud.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

import java.time.Instant;
import java.util.List;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public void saveActivityForPatient(Patient patient, HealthData healthData) {
        // Создаем новый объект Activity на основе данных о здоровье
        Activity activity = new Activity(
                healthData.getEnergy(),
                healthData.getSteps(),
                healthData.getHeartRate(),
                healthData.getStressState(),
                healthData.getStressLevel(),
                healthData.getSleepDuration(),
                healthData.getTimestamp() // Используем преобразованный Date
        );

        // Устанавливаем пациента для активности
        activity.setPatient(patient);

        // Сохраняем активность в базе данных
        activityRepository.save(activity);
    }

    public List<Activity> getActivitiesByPatientId(Long patientId) {
        return activityRepository.findByPatientId(patientId);
    }

}