package com.sheandstud.model;

import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class HealthData {
    private int energy;
    private int steps;
    private int heartRate;
    private String stressState;
    private float stressLevel;
    private int sleepDuration;


    private Long timestamp;

    // Конструктор, геттеры и сеттеры
    // ...

    public int getEnergy() {
        return energy;
    }

    public int getSteps() {
        return steps;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public String getStressState() {
        return stressState;
    }

    public float getStressLevel() {
        return stressLevel;
    }

    public int getSleepDuration() {
        return sleepDuration;
    }

    public Long getTimestamp() {
        return timestamp;
    }



    @Override
    public String toString() {
        return "HealthData{" +
                "energy=" + energy +
                ", steps=" + steps +
                ", heartRate=" + heartRate +
                ", stressState='" + stressState + '\'' +
                ", stressLevel=" + stressLevel +
                ", sleepDuration=" + sleepDuration +
                ", timestamp=" + timestamp +
                '}';
    }
}