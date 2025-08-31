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
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.io.StringWriter;
import com.opencsv.CSVWriter;
import java.util.Optional;

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

    public String generateReportAsCsv() {
        List<TestCase> testCases = testCaseRepository.findAll();
        if (testCases == null || testCases.isEmpty()) {
            return "TestCase ID,Name,Type,Status,Result ID,Result Status,Timestamp\nNo data available";
        }

        StringWriter writer = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(writer);

        try {
            // Write header
            String[] header = {"TestCase ID", "Name", "Type", "Status", "Result ID", "Result Status", "Timestamp"};
            csvWriter.writeNext(header);

            // Write data with null checks
            for (TestCase testCase : testCases) {
                TestResult result = testResultRepository.findByTestCaseId(testCase.getId())
                        .orElse(null);
                if (testCase.getId() == null) {
                    continue; // Skip invalid test cases
                }
                String[] data = {
                        String.valueOf(testCase.getId()),
                        testCase.getName() != null ? testCase.getName() : "N/A",
                        testCase.getType() != null ? testCase.getType() : "N/A",
                        testCase.getStatus() != null ? testCase.getStatus() : "N/A",
                        result != null ? String.valueOf(result.getId()) : "N/A",
                        result != null ? result.getStatus() : "N/A",
                        result != null ? result.getTimestamp().toString() : "N/A"
                };
                csvWriter.writeNext(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "TestCase ID,Name,Type,Status,Result ID,Result Status,Timestamp\nError generating report: " + e.getMessage();
        } finally {
            try {
                csvWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return writer.toString();
    }
}