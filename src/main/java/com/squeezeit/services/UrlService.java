package com.squeezeit.services;

import com.squeezeit.entities.RedirectEntity;
import com.squeezeit.repositories.RedirectRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
public class UrlService {

    private final RedirectRepository redirectRepository;

    @Value("${app.domain}")
    private String domain;

    @Autowired
    public UrlService(RedirectRepository redirectRepository) {
        this.redirectRepository = redirectRepository;
    }

    public RedirectEntity createShortUrl(String longUrl) {

        String shortUrlId = genRandomShortUrlId();
        while(!redirectRepository.findAllById(Collections.singleton(shortUrlId)).isEmpty())
            shortUrlId = genRandomShortUrlId();

        RedirectEntity redirectEntity = RedirectEntity.builder()
                .shortUrlId(shortUrlId)
                .longUrl(longUrl)
                .timesUsed(0)
                .build();

        redirectRepository.save(redirectEntity);

        redirectEntity.setShortUrlId(domain + shortUrlId);
        return redirectEntity;
    }

    private String genRandomShortUrlId() {

        String shortUrlId = RandomStringUtils.randomAlphanumeric(10);

        return shortUrlId;
    }

    public String fetchLongUrl(String shortUrlId) {
        Optional<RedirectEntity> redirectEntity = redirectRepository.findById(shortUrlId);

        if(redirectEntity.isEmpty())
            return "http://localhost:8080/";

        return redirectEntity.get().getLongUrl();
    }
}
