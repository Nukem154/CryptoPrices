package com.example.cryptoprices.service;

import com.example.cryptoprices.dto.CryptoPriceDto;
import com.example.cryptoprices.enums.CryptoNameEnum;
import com.example.cryptoprices.model.Cryptocurrency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class CryptocurrencyServiceTest {

    @Autowired
    private CryptocurrencyService cryptocurrencyService;

    private final CryptoNameEnum cryptoName = CryptoNameEnum.ETH;

    @Test
    void loadDataTest() {
        Cryptocurrency cryptocurrency = cryptocurrencyService.loadData(cryptoName);
        assertEquals(cryptoName, cryptocurrency.getCryptoName());
        assertFalse(cryptocurrency.getAsks().isEmpty());
    }


    @Test
    void findPricesByNameInBoundsTest() {
        int size = 4;
        List<CryptoPriceDto> list = cryptocurrencyService.findPricesByNameInBounds(cryptoName, 5, size);
        assertEquals(cryptoName, list.get(0).getCryptoName());
        assertEquals(size, list.size());
    }
}
