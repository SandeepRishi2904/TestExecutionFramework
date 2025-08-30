package com.example.TestExecutionFramework.service;

import com.example.TestExecutionFramework.entity.TestCase;
import com.example.TestExecutionFramework.entity.TestResult;
import com.example.TestExecutionFramework.repository.TestCaseRepository;
import com.example.TestExecutionFramework.repository.TestResultRepository;
import com.example.TestExecutionFramework.tests.api.GitHubApiTest;
import com.example.TestExecutionFramework.tests.ui.GoogleSearchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.concurrent.CompletableFuture;

@Service
public class TestExecutionService {

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Autowired
    private TestResultRepository testResultRepository;

    @Async
    public CompletableFuture<TestResult> executeTest(Long id) {
        TestCase testCase = testCaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TestCase not found"));

        boolean resultBool;
        if ("UI".equals(testCase.getType())) {
            resultBool = new GoogleSearchTest().runTest();
        } else if ("API".equals(testCase.getType())) {
            resultBool = new GitHubApiTest().runTest();
        } else {
            throw new RuntimeException("Unknown test type: " + testCase.getType());
        }

        String status = resultBool ? "PASSED" : "FAILED";

        TestResult testResult = new TestResult();
        testResult.setStatus(status);
        testResult.setTimestamp(new Timestamp(System.currentTimeMillis()));
        testResult.setTestCase(testCase);

        testResult = testResultRepository.save(testResult);
        testCase.setStatus(status);
        testCaseRepository.save(testCase);

        return CompletableFuture.completedFuture(testResult);
    }
}