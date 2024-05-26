package com.sheandstud.model;

public class HealthDataWithEmail {
    private String email;
    private HealthData healthData;

    // Конструктор, геттеры и сеттеры
    // ...

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHealthData(HealthData healthData) {
        this.healthData = healthData;
    }

    public HealthData getHealthData() {
        return healthData;
    }

    @Override
    public String toString() {
        return "HealthDataWithEmail{" +
                "email='" + email + '\'' +
                ", healthData=" + healthData +
                '}';
    }
}