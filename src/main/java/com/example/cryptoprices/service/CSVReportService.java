package com.example.cryptoprices.service;

import com.example.cryptoprices.enums.CryptoNameEnum;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CSVReportService {

    public static String[] HEADERS = {"Name", "Min Price", "Max Price"};
    public static String fileName = "report.csv";

    private final CryptocurrencyService cryptocurrencyService;

    public UrlResource downloadCSVReport() throws IOException {
        return new UrlResource(generateCSVReport().toURI());
    }

    public File generateCSVReport() {
        final File file = new File(fileName);

        try (CSVWriter writer = (CSVWriter) new CSVWriterBuilder(new FileWriter(file))
                .withQuoteChar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator('\t')
                .build()) {

            writer.writeNext(HEADERS);

            for (CryptoNameEnum name : CryptoNameEnum.values()) {
                writer.writeNext(getNextLine(name));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private String[] getNextLine(final CryptoNameEnum name) {
        final double minPrice = cryptocurrencyService.getMinPriceByName(name).getOrder().getPrice();
        final double maxPrice = cryptocurrencyService.getMaxPriceByName(name).getOrder().getPrice();
        return new String[]{name.name(), Double.toString(minPrice), Double.toString(maxPrice)};
    }
}
