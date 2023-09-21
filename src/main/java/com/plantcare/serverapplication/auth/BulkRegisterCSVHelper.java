package com.plantcare.serverapplication.auth;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;


public class BulkRegisterCSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERS = { "Email", "Username", "Firstname", "Lastname", "Password" };

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<BulkRegisterFarmerRequestDto> csvToBulkRegisterFarmerRequest(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<BulkRegisterFarmerRequestDto> registerRequestList = new ArrayList<BulkRegisterFarmerRequestDto>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                BulkRegisterFarmerRequestDto request = BulkRegisterFarmerRequestDto
                        .builder()
                        .email(csvRecord.get("Email"))
                        .username(csvRecord.get("Username"))
                        .firstName(csvRecord.get("Firstname"))
                        .lastName(csvRecord.get("Lastname"))
                        .password(csvRecord.get("Password"))
                        .build();

                registerRequestList.add(request);
            }

            return registerRequestList;
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }
    }


}