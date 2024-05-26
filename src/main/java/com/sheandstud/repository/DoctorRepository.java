package com.sheandstud.repository;
import com.sheandstud.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sheandstud.model.Doctor;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("SELECT d FROM Doctor d JOIN d.user u WHERE u.email = :email")
    Doctor findByUserEmail(@Param("email") String email);

    Doctor findByUserId(Long userId);
}