package com.sheandstud.model;

import javax.persistence.*;
import java.sql.Date;
import java.time.Instant;

@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "energy")
    private int energy;

    @Column(name = "steps")
    private int steps;

    @Column(name = "heartRate")
    private int heartRate;

    @Column(name = "stressState")
    private String stressState;

    @Column(name = "stressLevel")
    private float stressLevel;

    @Column(name = "sleepDuration")
    private int sleepDuration;

    @Column(name = "timeStamp")
    private Long timeStamp;

    @ManyToOne
    private Patient patient;

    public Activity() {
    }

    public Activity(int energy, int steps, int heartRate, String stressState, float stressLevel, int sleepDuration, Long timeStamp) {
        this.energy = energy;
        this.heartRate = heartRate;
        this.steps = steps;
        this.stressState = stressState;
        this.stressLevel = stressLevel;
        this.sleepDuration = sleepDuration;
        this.timeStamp = timeStamp;
    }

    public Activity(int energy, int steps, int heartRate, String stressState, float stressLevel, int sleepDuration, Date timeStamp) {
    }

    public long getId() {
        return id;
    }

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

    public Long getTimeStamp() {
        return timeStamp;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public void setStressState(String stressState) {
        this.stressState = stressState;
    }

    public void setStressLevel(float stressLevel) {
        this.stressLevel = stressLevel;
    }

    public void setSleepDuration(int sleepDuration) {
        this.sleepDuration = sleepDuration;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", energy=" + energy +
                ", steps=" + steps +
                ", heartRate=" + heartRate +
                ", stressState='" + stressState + '\'' +
                ", stressLevel=" + stressLevel +
                ", sleepDuration=" + sleepDuration +
                ", timeStamp=" + timeStamp +
                ", patient=" + patient +
                '}';
    }
}
