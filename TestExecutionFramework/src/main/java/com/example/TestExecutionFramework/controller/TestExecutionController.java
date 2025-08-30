package com.example.TestExecutionFramework.controller;

import com.example.TestExecutionFramework.entity.TestResult;
import com.example.TestExecutionFramework.service.TestExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tests")
public class TestExecutionController {

    @Autowired
    private TestExecutionService service;

    @PostMapping("/run/{id}")
    public TestResult runSingleTest(@PathVariable Long id) {
        return service.executeTest(id).join(); // Blocks until completion
    }

    @PostMapping("/run/batch")
    public List<TestResult> runBatchTests(@RequestBody List<Long> ids) {
        List<CompletableFuture<TestResult>> futures = ids.stream()
                .map(id -> service.executeTest(id))
                .collect(Collectors.toList());

        return futures.stream()
                .map(CompletableFuture::join) // Wait for all to complete
                .collect(Collectors.toList());
    }
}