package com.sheandstud.security;

import com.sheandstud.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
               //.csrf().disable() // Отключаем CSRF-защиту
                .authorizeRequests()
                .antMatchers("/login").permitAll() // Разрешаем доступ к странице входа всем
                .antMatchers("/api/healthdata").permitAll() // Разрешить доступ к /api/healthdata без аутентификации
                .antMatchers("/api/**").permitAll() // Разрешить доступ ко всем URL-адресам, начинающимся с /api/, без аутентификации
                .antMatchers("/register/doctor").permitAll()
                .antMatchers("/doctors/**").hasRole("DOCTOR") // Используем префикс ROLE_
                .antMatchers("/patient/**").hasRole("PATIENT") // Используем префикс ROLE_
                .anyRequest().authenticated() // Все остальные URL-адреса требуют аутентификации
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email") // Указываем, что в качестве имени пользователя используется email
                .successHandler(successHandler) // Указываем наш обработчик успешной аутентификации
                .permitAll()
                .and()
                .logout()
                .permitAll();

        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true); // Разрешаем кодированные слэши в URL
        firewall.setAllowSemicolon(true); // Разрешаем использование точки с запятой
        firewall.setAllowUrlEncodedPercent(true); // Разрешаем кодированные символы процента в URL
        firewall.setAllowUrlEncodedPeriod(true); // Разрешаем кодированные точки в URL


    }

    @Bean
    public CustomAuthenticationSuccessHandler successHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            // Проверяем роли пользователя и определяем URL для перенаправления
            if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("DOCTOR"))) {
                response.sendRedirect("/doctors/doctor");
            } else if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("PATIENT"))) {
                response.sendRedirect("/patient");
            } else {
                response.sendRedirect("/default"); // Перенаправление по умолчанию для других ролей
            }
        };
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}