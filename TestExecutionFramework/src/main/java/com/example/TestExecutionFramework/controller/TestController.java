package com.example.TestExecutionFramework.controller;

import com.example.TestExecutionFramework.entity.TestCase;
import com.example.TestExecutionFramework.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tests")
public class TestController {

    @Autowired
    private TestCaseRepository repository;

    @PostMapping
    public ResponseEntity<List<TestCase>> createTestCase(@RequestBody List<TestCase> testCases) {
        if (testCases == null || testCases.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<TestCase> savedTestCases = repository.saveAll(testCases);
        return ResponseEntity.ok(savedTestCases);
    }
}

//[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30]