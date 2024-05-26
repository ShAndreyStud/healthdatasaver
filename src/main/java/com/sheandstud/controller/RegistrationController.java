package com.sheandstud.controller;

import com.sheandstud.model.Doctor;
import com.sheandstud.model.Patient;
import com.sheandstud.model.User;
import com.sheandstud.repository.DoctorRepository;
import com.sheandstud.repository.PatientRepository;
import com.sheandstud.repository.UserRepository;
import com.sheandstud.service.DoctorService;
import com.sheandstud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/register/doctor")
    public String registerDoctor(@RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 @RequestParam("surname") String surname,
                                 @RequestParam("name") String name,
                                 @RequestParam("patronymic") String patronymic,
                                 @RequestParam("specialization") String specialization,
                                 @RequestParam("phone") String phone) {
        User user = userService.registerUser(email, password, User.Role.DOCTOR);
        doctorService.registerDoctor(user, surname, name, patronymic, specialization, phone);
        return "redirect:/login";
    }
}
