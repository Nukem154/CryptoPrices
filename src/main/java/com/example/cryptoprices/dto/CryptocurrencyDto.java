package com.example.cryptoprices.dto;

import com.example.cryptoprices.enums.CryptoNameEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CryptocurrencyDto {
    private long id;
    private CryptoNameEnum cryptoName;
    private long timestamp;
    private List<List<Double>> asks;
    private String pair;
    private String sell_total;
}
