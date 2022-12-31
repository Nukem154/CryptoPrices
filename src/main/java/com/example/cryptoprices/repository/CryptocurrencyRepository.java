package com.example.cryptoprices.repository;

import com.example.cryptoprices.enums.CryptoNameEnum;
import com.example.cryptoprices.model.Cryptocurrency;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CryptocurrencyRepository extends MongoRepository<Cryptocurrency, Long> {
    Optional<Cryptocurrency> findFirstByCryptoNameOrderByTimestampDesc(CryptoNameEnum cryptoName);
}
