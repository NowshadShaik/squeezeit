package com.squeezeit.services;

import com.squeezeit.entities.RedirectEntity;
import com.squeezeit.repositories.RedirectRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UrlService {

    private final RedirectRepository redirectRepository;

    @Autowired
    public UrlService(RedirectRepository redirectRepository) {
        this.redirectRepository = redirectRepository;
    }

    public RedirectEntity createShortUrl(String longUrl) {

        String shortUrl = genRandomShortUrl();
        while(redirectRepository.findByShortUrl(shortUrl).isPresent())
            shortUrl = genRandomShortUrl();

        RedirectEntity redirectEntity = RedirectEntity.builder()
                .uuid(UUID.randomUUID())
                .longUrl(longUrl)
                .shortUrl(shortUrl)
                .timesUsed(0)
                .build();

        redirectRepository.save(redirectEntity);

        return redirectEntity;
    }

    private String genRandomShortUrl() {

        String shortUrl = "http://localhost:8080/";
        shortUrl = shortUrl.concat(RandomStringUtils.randomAlphanumeric(10));

        return shortUrl;
    }
}
