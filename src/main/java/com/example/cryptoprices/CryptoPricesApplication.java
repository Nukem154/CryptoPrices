package com.example.cryptoprices;

import com.example.cryptoprices.enums.CryptoNameEnum;
import com.example.cryptoprices.service.CryptocurrencyService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
public class CryptoPricesApplication {

    private final CryptocurrencyService cryptocurrencyService;

    public CryptoPricesApplication(CryptocurrencyService cryptocurrencyService) {
        this.cryptocurrencyService = cryptocurrencyService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CryptoPricesApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Arrays.stream(CryptoNameEnum.values()).forEach(name -> System.out.println(cryptocurrencyService.loadData(name)));
        };
    }
}
