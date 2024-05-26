package com.sheandstud.service;

import com.sheandstud.exceptions.ResourceNotFoundException;
import com.sheandstud.model.Doctor;
import com.sheandstud.model.Patient;
import com.sheandstud.model.User;
import com.sheandstud.repository.DoctorRepository;
import com.sheandstud.repository.PatientRepository;
import com.sheandstud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import com.sheandstud.repository.DoctorRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private UserRepository userRepository;



    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor registerDoctor(User user, String surname, String name, String patronymic, String specialization, String phone) {
        Doctor doctor = new Doctor();
        doctor.setUser(user);
        doctor.setSurname(surname);
        doctor.setName(name);
        doctor.setPatronymic(patronymic);
        doctor.setSpecialization(specialization);
        doctor.setPhone(phone);
        return doctorRepository.save(doctor);
    }


    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor findDoctorByEmail(String email) {
        return doctorRepository.findByUserEmail(email);
    }

    public Doctor findDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }
}