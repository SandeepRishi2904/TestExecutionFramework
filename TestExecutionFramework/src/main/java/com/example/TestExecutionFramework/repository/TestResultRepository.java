package com.example.TestExecutionFramework.repository;

import com.example.TestExecutionFramework.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {
}