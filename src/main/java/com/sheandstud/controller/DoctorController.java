package com.sheandstud.controller;
import com.sheandstud.model.User;
import com.sheandstud.repository.PatientRepository;
import com.sheandstud.service.DoctorService;
import com.sheandstud.service.PatientService;
import com.sheandstud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Set;
import java.util.List;
import com.sheandstud.repository.DoctorRepository;
import com.sheandstud.model.Doctor;
import com.sheandstud.model.Patient;
import com.sheandstud.exceptions.ResourceNotFoundException;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DoctorController(DoctorService doctorService, PasswordEncoder passwordEncoder) {
        this.doctorService = doctorService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private UserService userService;
    @Autowired
    private PatientService patientService;



    @GetMapping("/doctor")
    public String doctorPage() {
        return "doctor"; // Имя вашего шаблона Thymeleaf для страницы врача
    }


    @GetMapping("/doctor/{id}")
    public String showDoctorPage(@PathVariable("id") Long id, Model model) {
        Doctor doctor = doctorService.findDoctorById(id);
        if (doctor != null) {
            model.addAttribute("doctor", doctor);
            model.addAttribute("doctorId", id); // Передаем doctorId в модель
            model.addAttribute("patients", doctor.getPatients()); // Передаем список пациентов врача в модель
            return "doctor"; // Имя вашего шаблона Thymeleaf
        } else {
            // Обработка ситуации, когда врач не найден
            return "error"; // Имя страницы ошибки или редирект
        }
    }

    @PostMapping("/doctor/{id}/add_patient")
    public String registerPatient(@PathVariable("id") Long doctorId,
                                  @RequestParam("email") String email,
                                  @RequestParam("password") String password,
                                  @RequestParam("surname") String surname,
                                  @RequestParam("name") String name,
                                  @RequestParam("patronymic") String patronymic,
                                  @RequestParam("diagnosis") String diagnosis,
                                  @RequestParam("operation") String operation,
                                  @RequestParam("operationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate operationDate,
                                  @RequestParam("phone") String phone,
                                  Model model) {
        User user = userService.registerUser(email, password, User.Role.PATIENT);
        Doctor doctor = doctorService.findDoctorById(doctorId); // Получаем врача по идентификатору
        Patient patient = patientService.registerPatient(user, doctor, surname, name, patronymic, diagnosis, operation, operationDate, phone);

        // Обновляем список пациентов врача в модели
        model.addAttribute("patients", doctor.getPatients());

        // Возвращаем шаблон "doctor" для отображения страницы врача с обновленным списком пациентов
        return "redirect:/doctors/doctor/" + doctorId;
    }



}
