# TestExecutionFramework
Overview
The TestExecutionFramework is a Spring Boot application designed to manage and execute test cases (e.g., UI and API tests) and generate reports in CSV format. It integrates with a MySQL database to store test cases and their results, leveraging Spring Data JPA for persistence and OpenCSV for report generation. The framework supports automated test execution and provides a REST API for interaction.

**Features**

Create and manage test cases (UI and API types).
Execute tests asynchronously and store results.
Generate a downloadable CSV report of test cases and results.
Utilizes Selenium for UI testing and REST-assured for API testing.

**Prerequisites**

Java 21: Ensure JDK 21 is installed (e.g., from Oracle).
Maven 3.x: For building and managing dependencies.
MySQL 8.x: For the database.
IntelliJ IDEA (optional): For development and debugging.

**Setup Instructions**

1. Clone the Repository
git clone <repository-url>
cd TestExecutionFramework

2. Configure the Database

Install MySQL and create a database:CREATE DATABASE test_execution_db;
CREATE USER 'testuser'@'localhost' IDENTIFIED BY 'testpass';
GRANT ALL PRIVILEGES ON test_execution_db.* TO 'testuser'@'localhost';
FLUSH PRIVILEGES;


Update src/main/resources/application.properties with your MySQL credentials:spring.datasource.url=jdbc:mysql://localhost:3306/test_execution_db
spring.datasource.username=testuser
spring.datasource.password=testpass
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update

3. Build the Project

Run the following command to build and resolve dependencies:mvn clean install

4. Run the Application

Start the Spring Boot application:mvn spring-boot:run


The application will be available at http://localhost:8080.

5. Populate Test Data

Use a tool like Postman or curl to add test cases:curl -X POST -H "Content-Type: application/json" -d '[{"name":"Test1","type":"UI"},{"name":"Test2","type":"API"}]' http://localhost:8080/tests


Execute the tests:curl -X POST -H "Content-Type: application/json" -d '[1, 2]' http://localhost:8080/tests/run/batch



**Usage
Endpoints**

Create Test Cases: POST /tests
Body: [{"name":"TestName","type":"UI|API"}]
Response: List of created TestCase IDs.


Run Tests: POST /tests/run/batch
Body: [1, 2, ...] (array of testCaseIds)
Response: Status of execution.


Download Report: GET /tests/report/csv
Response: Downloads test_report.csv with columns: TestCase ID, Name, Type, Status, Result ID, Result Status, Timestamp.



**Download Report Programmatically**
A utility class ReportDownloader is included to fetch the CSV report:

Location: src/main/java/com/example/TestExecutionFramework/util/ReportDownloader.java
Run via IntelliJ or command line:& "C:\Program Files\Java\jdk-21\bin\java.exe" "-javaagent:D:\IntelliJ IDEA Community Edition 2025.1.4.1\lib\idea_rt.jar=57523" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath "E:\SpringBoot\TestExecutionFramework\TestExecutionFramework\target\classes;C:\Users\jbsan\.m2\repository\org\springframework\boot\spring-boot-starter-data-jpa\3.5.5\spring-boot-starter-data-jpa-3.5.5.jar;[...rest of the classpath...]" com.example.TestExecutionFramework.util.ReportDownloader


Ensure the application is running before executing.

**Troubleshooting**
Common Issues

HTTP 500 Error: If test_report.csv contains "Error generating report: Query did not return a unique result", multiple TestResult entries exist for a testCaseId. Fix by:
Updating TestResultRepository.findByTestCaseId to:@Query("SELECT tr FROM TestResult tr WHERE tr.testCase.id = :testCaseId ORDER BY tr.timestamp DESC LIMIT 1")
Optional<TestResult> findByTestCaseId(@Param("testCaseId") Long testCaseId);


Cleaning duplicates in the database:DELETE t1 FROM test_result t1
INNER JOIN test_result t2
WHERE t1.test_case_id = t2.test_case_id
AND t1.timestamp < t2.timestamp;




Database Connection Failure: Verify application.properties settings and ensure MySQL is running.
PowerShell Command Error: Use the & operator and quote the classpath as shown above.

**Logs**

Enable debug logging by adding to application.properties:logging.level.org.springframework=DEBUG
logging.level.com.example=DEBUG


Check the console output for stack traces.

**Contributing**

Fork the repository.
Create a feature branch: git checkout -b feature-name.
Commit changes: git commit -m "Description".
Push and submit a pull request.

**License**
This project is licensed under the MIT License - see the LICENSE file for details.
