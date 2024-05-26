package com.sheandstud.controller;

import com.sheandstud.model.Activity;
import com.sheandstud.model.Patient;
import com.sheandstud.service.ActivityService;
import com.sheandstud.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class PatientInfoController {

    private final PatientService patientService;
    private final ActivityService activityService;

    @Autowired
    public PatientInfoController(PatientService patientService, ActivityService activityService) {
        this.patientService = patientService;
        this.activityService = activityService;
    }

    @GetMapping("/patient_info")
    public String patientInfo(@RequestParam("patientId") Long patientId, Model model) {
        Patient patient = patientService.getPatientById(patientId);
        List<Activity> activities = activityService.getActivitiesByPatientId(patientId);

        // Преобразуем данные в формат, который Chart.js может понять
        List<Map<String, Object>> energyData = activities.stream()
                .map(activity -> {
                    Map<String, Object> map = new HashMap<>();
                    Date date = new Date(activity.getTimeStamp());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                    map.put("x", sdf.format(date));
                    map.put("y", activity.getEnergy());
                    return map;
                })
                .collect(Collectors.toList());

        // Повторяем для остальных данных
        List<Map<String, Object>> stepsData = activities.stream()
                .map(activity -> {
                    Map<String, Object> map = new HashMap<>();
                    Date date = new Date(activity.getTimeStamp());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                    map.put("x", sdf.format(date));
                    map.put("y", activity.getSteps());
                    return map;
                })
                .collect(Collectors.toList());


        List<Map<String, Object>> heartRateData = activities.stream()
                .map(activity -> {
                    Map<String, Object> map = new HashMap<>();
                    Date date = new Date(activity.getTimeStamp());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                    map.put("x", sdf.format(date));
                    map.put("y", activity.getHeartRate());
                    return map;
                })
                .collect(Collectors.toList());

        List<Map<String, Object>> stressLevelData = activities.stream()
                .map(activity -> {
                    Map<String, Object> map = new HashMap<>();
                    Date date = new Date(activity.getTimeStamp());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                    map.put("x", sdf.format(date));
                    map.put("y", activity.getStressLevel());
                    return map;
                })
                .collect(Collectors.toList());

        List<Map<String, Object>> sleepDurationData = activities.stream()
                .map(activity -> {
                    Map<String, Object> map = new HashMap<>();
                    Date date = new Date(activity.getTimeStamp());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                    map.put("x", sdf.format(date));
                    map.put("y", activity.getSleepDuration());
                    return map;
                })
                .collect(Collectors.toList());

        // Добавляем данные в модель для передачи в шаблон
        model.addAttribute("energyGraphData", energyData);
        model.addAttribute("stepsGraphData", stepsData);
        model.addAttribute("heartRateGraphData", heartRateData);
        model.addAttribute("stressLevelGraphData", stressLevelData);
        model.addAttribute("sleepDurationGraphData", sleepDurationData);


        model.addAttribute("patient", patient);

        return "patient_info";
    }
}