package com.squeezeit.services;

import com.squeezeit.Exceptions.NoSuchUrlException;
import com.squeezeit.entities.RedirectData;
import com.squeezeit.models.Statistics;
import com.squeezeit.repositories.RedirectRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
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

    public String createShortUrl(String longUrl) {

        String shortUrlId = genRandomShortUrlId();
        while(!redirectRepository.findAllById(Collections.singleton(shortUrlId)).isEmpty())
            shortUrlId = genRandomShortUrlId();

        RedirectData redirectData = RedirectData.builder()
                .shortUrlId(shortUrlId)
                .longUrl(longUrl)
                .clickCount(0)
                .createdTimestamp(ZonedDateTime.now())
                .build();

        redirectRepository.save(redirectData);

        return domain + shortUrlId;
    }

    private static String genRandomShortUrlId() {
        return RandomStringUtils.randomAlphanumeric(8);
    }

    public String fetchLongUrl(String shortUrlId) throws NoSuchUrlException {
        Optional<RedirectData> redirectDataDB = redirectRepository.findById(shortUrlId);

        if(redirectDataDB.isEmpty())
            throw new NoSuchUrlException("Invalid URL...");

        RedirectData redirectData = redirectDataDB.get();
        redirectData.incrementClickCount();
        redirectRepository.save(redirectData);

        return redirectData.getLongUrl();
    }

    public Statistics fetchStatistics(String shortUrlId) throws NoSuchUrlException {

        Optional<RedirectData> redirectData = redirectRepository.findById(shortUrlId);

        if(redirectData.isEmpty())
            throw new NoSuchUrlException("Invalid URL...");

        Statistics stats = new Statistics(redirectData.get().getCreatedTimestamp(), redirectData.get().getClickCount());

        return stats;
    }
}
