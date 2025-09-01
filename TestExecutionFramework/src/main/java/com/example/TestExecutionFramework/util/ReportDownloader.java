package com.example.TestExecutionFramework.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class ReportDownloader {
    public static void main(String[] args) throws IOException {
        URL website = new URL("http://localhost:8080/tests/report/csv");
        try (ReadableByteChannel readableByteChannel = Channels.newChannel(website.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("test_report.csv")) {
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        }
        System.out.println("Report downloaded as test_report.csv");
    }
}