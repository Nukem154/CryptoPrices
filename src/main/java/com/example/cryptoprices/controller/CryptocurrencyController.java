package com.example.cryptoprices.controller;

import com.example.cryptoprices.dto.CryptoPriceDto;
import com.example.cryptoprices.enums.CryptoNameEnum;
import com.example.cryptoprices.service.CSVReportService;
import com.example.cryptoprices.service.CryptocurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/cryptocurrencies")
@RequiredArgsConstructor
public class CryptocurrencyController {

    private final CryptocurrencyService cryptocurrencyService;
    private final CSVReportService csvReportService;

    @GetMapping
    public List<CryptoPriceDto> getByName(@RequestParam("currency_name") CryptoNameEnum cryptoName,
                                          @RequestParam(value = "page_number", defaultValue = "0") int pageNumber,
                                          @RequestParam(value = "page_size", defaultValue = "10") int pageSize) {
        return cryptocurrencyService.findPricesByNameInBounds(cryptoName, pageNumber, pageSize);
    }

    @GetMapping("/minprice")
    public CryptoPriceDto getMinPriceByName(@RequestParam CryptoNameEnum name) {
        return cryptocurrencyService.getMinPriceByName(name);
    }

    @GetMapping("/maxprice")
    public CryptoPriceDto getMaxPriceByName(@RequestParam CryptoNameEnum name) {
        return cryptocurrencyService.getMaxPriceByName(name);
    }

    @GetMapping("/csv")
    public ResponseEntity<?> getCsvReport() throws IOException {
        final Resource resource = csvReportService.downloadCSVReport();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
