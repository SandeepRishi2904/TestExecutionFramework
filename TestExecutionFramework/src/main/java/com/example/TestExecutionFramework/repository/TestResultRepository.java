//package com.example.TestExecutionFramework.repository;
//
//import com.example.TestExecutionFramework.entity.TestResult;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.Optional;
//
//public interface TestResultRepository extends JpaRepository<TestResult, Long> {
//    @Query("SELECT tr FROM TestResult tr WHERE tr.testCase.id = :testCaseId")
//    Optional<TestResult> findByTestCaseId(@Param("testCaseId") Long testCaseId);
//}
package com.example.TestExecutionFramework.repository;

import com.example.TestExecutionFramework.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    @Query("SELECT tr FROM TestResult tr WHERE tr.testCase.id = :testCaseId ORDER BY tr.timestamp DESC LIMIT 1")
    Optional<TestResult> findByTestCaseId(@Param("testCaseId") Long testCaseId);
}