package com.example.cryptoprices.service;

import com.example.cryptoprices.dto.CryptocurrencyDto;
import com.example.cryptoprices.enums.CryptoNameEnum;
import com.example.cryptoprices.dto.CryptoPriceDto;
import com.example.cryptoprices.model.Cryptocurrency;
import com.example.cryptoprices.model.Order;
import com.example.cryptoprices.repository.CryptocurrencyRepository;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CryptocurrencyService {
    private final RestTemplate restTemplate;
    private final CryptocurrencyRepository cryptocurrencyRepository;
    private final ModelMapper modelMapper;

    public CryptocurrencyService(@Lazy RestTemplate restTemplate, CryptocurrencyRepository cryptocurrencyRepository, @Lazy ModelMapper modelMapper) {
        this.restTemplate = restTemplate;
        this.cryptocurrencyRepository = cryptocurrencyRepository;
        this.modelMapper = modelMapper;
    }

    public Cryptocurrency loadData(final CryptoNameEnum cryptoType) {
        final CryptocurrencyDto cryptocurrencyDto = new Gson().fromJson(loadActualData(cryptoType).getBody(), CryptocurrencyDto.class);
        cryptocurrencyDto.setCryptoName(cryptoType);
        final Cryptocurrency cryptocurrency = cryptoFromDto(cryptocurrencyDto);
        return cryptocurrencyRepository.save(cryptocurrency);
    }

    private ResponseEntity<String> loadActualData(final CryptoNameEnum cryptoType) {
        return restTemplate.getForEntity("https://cex.io/api/order_book/" + cryptoType + "/USD", String.class);
    }

    private Cryptocurrency cryptoFromDto(final CryptocurrencyDto cryptocurrencyDto) {
        final Cryptocurrency cryptocurrency = modelMapper.map(cryptocurrencyDto, Cryptocurrency.class);
        cryptocurrency.setAsks(convertRawListToOrders(cryptocurrencyDto.getAsks()));
        return cryptocurrency;
    }

    private List<Order> convertRawListToOrders(final List<List<Double>> arrays) {
        return arrays.stream().map((array) -> new Order(array.get(0), array.get(1))).collect(Collectors.toList());
    }

    public CryptoPriceDto getMinPriceByName(final CryptoNameEnum cryptoName) {
        return CryptoPriceDto.getMinPriceDto(findActualCryptoDataByName(cryptoName));
    }

    public CryptoPriceDto getMaxPriceByName(final CryptoNameEnum cryptoName) {
        return CryptoPriceDto.getMaxPriceDto(findActualCryptoDataByName(cryptoName));
    }

    public List<CryptoPriceDto> findPricesByNameInBounds(final CryptoNameEnum cryptoName, int page, int size) {
        if(page < 0 || size <= 0) throw new IllegalArgumentException();
        final List<CryptoPriceDto> list = CryptoPriceDto.toPriceList(findActualCryptoDataByName(cryptoName));
        return list.size() > page*size+size ? list.subList(page*size, page*size+size) : new ArrayList<>();
    }

    private Cryptocurrency findActualCryptoDataByName(final CryptoNameEnum cryptoName) {
        return cryptocurrencyRepository.findFirstByCryptoNameOrderByTimestampDesc(cryptoName)
                .orElseThrow(NoSuchElementException::new);
    }
}
