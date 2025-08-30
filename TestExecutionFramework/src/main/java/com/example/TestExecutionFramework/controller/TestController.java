package com.example.TestExecutionFramework.controller;

import com.example.TestExecutionFramework.entity.TestCase;
import com.example.TestExecutionFramework.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tests")
public class TestController {

    @Autowired
    private TestCaseRepository repository;

    @PostMapping
    public TestCase createTestCase(@RequestBody TestCase testCase) {
        return repository.save(testCase);
    }
}