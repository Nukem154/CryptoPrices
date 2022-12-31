package com.example.cryptoprices.dto;

import com.example.cryptoprices.enums.CryptoNameEnum;
import com.example.cryptoprices.model.Cryptocurrency;
import com.example.cryptoprices.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CryptoPriceDto {
    private CryptoNameEnum cryptoName;
    private Order order;

    public static List<CryptoPriceDto> toPriceList(final Cryptocurrency cryptocurrency) {
        return cryptocurrency.getAsks().stream()
                .map(order -> new CryptoPriceDto(cryptocurrency.getCryptoName(), order))
                .collect(Collectors.toList());
    }

    public static CryptoPriceDto getMinPriceDto(final Cryptocurrency cryptocurrency) {
        return new CryptoPriceDto(cryptocurrency.getCryptoName(), getMinPriceOrder(cryptocurrency));
    }

    public static CryptoPriceDto getMaxPriceDto(final Cryptocurrency cryptocurrency) {
        return new CryptoPriceDto(cryptocurrency.getCryptoName(), getMaxPriceOrder(cryptocurrency));
    }

    private static Order getMinPriceOrder(final Cryptocurrency cryptocurrency) {
        return cryptocurrency.getAsks().get(0);
    }

    private static Order getMaxPriceOrder(final Cryptocurrency cryptocurrency) {
        return cryptocurrency.getAsks().get(cryptocurrency.getAsks().size()-1);
    }
}
