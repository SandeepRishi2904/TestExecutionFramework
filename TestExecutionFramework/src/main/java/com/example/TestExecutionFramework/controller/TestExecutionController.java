package com.example.TestExecutionFramework.controller;

import com.example.TestExecutionFramework.entity.TestResult;
import com.example.TestExecutionFramework.service.TestExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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
        return service.executeTest(id).join();
    }

    @PostMapping("/run/batch")
    public List<TestResult> runBatchTests(@RequestBody List<Long> ids) {
        List<CompletableFuture<TestResult>> futures = ids.stream()
                .map(id -> service.executeTest(id))
                .collect(Collectors.toList());

        return futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    @GetMapping("/report/csv")
    public ResponseEntity<InputStreamResource> downloadReport() throws IOException {
        String csvContent = service.generateReportAsCsv();
        ByteArrayInputStream in = new ByteArrayInputStream(csvContent.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=test_report.csv");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(in));
    }
}