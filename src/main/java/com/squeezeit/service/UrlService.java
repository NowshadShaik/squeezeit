package com.squeezeit.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UrlService {

    private static Map<String, String> urlStore = new HashMap<>();

    public String shorten(String longUrl) {

        if(urlStore.containsKey(longUrl)) {
            return urlStore.get(longUrl);
        }

        String shortUrl = squeezeUrl(longUrl);
        while(urlStore.containsKey(shortUrl))
            shortUrl = squeezeUrl(longUrl);

        urlStore.put(longUrl, shortUrl);

        System.out.println(urlStore);

        return shortUrl;
    }

    private String squeezeUrl(String url) {
        return RandomStringUtils.randomAlphanumeric(10);
    }
}
