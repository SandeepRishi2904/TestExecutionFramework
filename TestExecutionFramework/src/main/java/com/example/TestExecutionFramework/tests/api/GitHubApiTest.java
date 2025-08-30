package com.example.TestExecutionFramework.tests.api;

import io.restassured.RestAssured;

public class GitHubApiTest {

    public boolean runTest() {
        try {
            int statusCode = RestAssured.get("https://api.github.com").getStatusCode();
            return statusCode == 200;
        } catch (Exception e) {
            return false;
        }
    }
}