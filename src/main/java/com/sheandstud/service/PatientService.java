package com.sheandstud.service;
import com.sheandstud.model.Doctor;
import com.sheandstud.model.Patient;
import com.sheandstud.model.User;
import com.sheandstud.repository.DoctorRepository;
import com.sheandstud.repository.PatientRepository;
import com.sheandstud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;


@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    private final UserRepository userRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository, DoctorRepository doctorRepository, UserRepository userRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
    }

    public Patient registerPatient(User user, Doctor doctor, String surname, String name, String patronymic, String diagnosis, String operation, LocalDate operationDate, String phone) {
        Patient patient = new Patient();
        patient.setUser(user);
        patient.setDoctor(doctor);
        patient.setSurname(surname);
        patient.setName(name);
        patient.setPatronymic(patronymic);
        patient.setDiagnosis(diagnosis);
        patient.setPhone(phone);
        patient.setOperation(operation);
        patient.setOperationDate(operationDate);
        return patientRepository.save(patient);
    }


    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + id));
    }

    public Patient findPatientByEmail(String email) {
        // Ищем пользователя по электронной почте
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }
        // Ищем пациента по пользователю
        return patientRepository.findByUser(user);
    }

    public Patient findPatientById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }
}