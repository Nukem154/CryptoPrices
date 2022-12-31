package com.example.cryptoprices.enums;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToEnumConverter implements Converter<String, CryptoNameEnum> {
    @Override
    public CryptoNameEnum convert(String source) {
        return CryptoNameEnum.valueOf(source.toUpperCase());
    }
}
