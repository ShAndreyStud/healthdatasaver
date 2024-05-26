package com.sheandstud.service;

import com.sheandstud.model.Patient;
import com.sheandstud.model.Test;
import com.sheandstud.model.TestResult;
import com.sheandstud.repository.TestRepository;
import com.sheandstud.repository.TestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class TestService {
    private final TestResultRepository testResultRepository;
    private final TestRepository testRepository;

    @Autowired
    public TestService(TestResultRepository testResultRepository, TestRepository testRepository) {
        this.testResultRepository = testResultRepository;
        this.testRepository = testRepository;
    }

    public void submitTest(Patient patient, Test test, int score) {
        TestResult testResult = new TestResult();
        testResult.setPatient(patient);
        testResult.setTest(test);
        testResult.setScore(score);
        testResultRepository.save(testResult);
    }

    public Test getTestById(Long id) {
        return testRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Test not found with id: " + id));
    }
}
