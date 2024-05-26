package com.sheandstud.repository;

import com.sheandstud.model.Doctor;
import com.sheandstud.model.Patient;
import com.sheandstud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByUserEmail(String email);
    Patient findByUser(User user);

    Patient findByUserId(Long userId);
}
