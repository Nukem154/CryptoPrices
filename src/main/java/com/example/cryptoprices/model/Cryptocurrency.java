package com.example.cryptoprices.model;

import com.example.cryptoprices.enums.CryptoNameEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@Setter
@ToString
public class Cryptocurrency {
    @Id
    private long id;
    private CryptoNameEnum cryptoName;
    private long timestamp;
    private long timestamp_ms;
    private List<Order> asks;
    private String pair;
    private String sell_total;
}
