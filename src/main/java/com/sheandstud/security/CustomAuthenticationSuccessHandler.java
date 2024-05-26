package com.sheandstud.security;


import com.sheandstud.model.Patient;
import com.sheandstud.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sheandstud.model.Doctor;
import com.sheandstud.model.User;
import com.sheandstud.repository.DoctorRepository;
import com.sheandstud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_DOCTOR")) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String email = userDetails.getUsername(); // Получаем электронную почту пользователя

                User user = userRepository.findByEmail(email); // Ищем пользователя по электронной почте
                if (user != null) {
                    Doctor doctor = doctorRepository.findByUserId(user.getId()); // Ищем доктора по id пользователя
                    if (doctor != null) {
                        Long doctorId = doctor.getId();
                        response.sendRedirect("/doctors/doctor/" + doctorId ); //+ doctorId
                        return;
                    }
                }
            } else if (authority.getAuthority().equals("ROLE_PATIENT")) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String email = userDetails.getUsername(); // Получаем электронную почту пользователя

                User user = userRepository.findByEmail(email); // Ищем пользователя по электронной почте
                if (user != null) {
                    Patient patient = patientRepository.findByUserId(user.getId()); // Ищем доктора по id пользователя
                    if (patient != null) {
                        Long patientId = patient.getId();
                        response.sendRedirect("/patients/patient/" + patientId );
                        return;
                    }
                }
            }
        }
        // Если роль не соответствует ни одной из указанных выше, перенаправляем на страницу по умолчанию
        response.sendRedirect("/default");
    }
}