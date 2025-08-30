package com.example.TestExecutionFramework.repository;

import com.example.TestExecutionFramework.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
}