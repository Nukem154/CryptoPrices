package com.example.cryptoprices.service;

import com.example.cryptoprices.enums.CryptoNameEnum;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CSVReportServiceTest {

    @Autowired
    private CSVReportService csvReportService;

    @Test
    void generateCSVReport() throws IOException, CsvValidationException {
        File file = csvReportService.generateCSVReport();
        assertEquals(file.getName(), CSVReportService.fileName);

        CSVReader reader = new CSVReader(new FileReader(CSVReportService.fileName));

        String[] actualHeaders = reader.readNext()[0].split("\t");
        assertArrayEquals(CSVReportService.HEADERS, actualHeaders);

        Arrays.stream(CryptoNameEnum.values()).forEach(
                name -> {
                    try {
                        assertTrue(Arrays.stream(reader.readNext()[0].split("\t")).toList().contains(name.name()));
                    } catch (IOException | CsvValidationException e) {
                        e.printStackTrace();
                    }
                });

        assertNull(reader.readNext());
    }
}
