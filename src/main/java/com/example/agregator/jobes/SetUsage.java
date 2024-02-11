package com.example.agregator.jobes;

import com.example.agregator.repositories.ProductStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SetUsage {
    @Autowired
    ProductStorageRepository productStorageRepository;

    public void EnableUsage()
    {
        productStorageRepository.upMinUsage("-");
    }
}
