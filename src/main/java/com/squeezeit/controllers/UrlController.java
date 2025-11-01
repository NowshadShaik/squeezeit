package com.squeezeit.controllers;

import com.squeezeit.entities.RedirectEntity;
import com.squeezeit.services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shortenUrl")
public class UrlController {

    private final UrlService urlService;

    @Autowired
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    private ResponseEntity<RedirectEntity> shortenUrl(@RequestParam String URL) {

        RedirectEntity redirectEntity = urlService.createShortUrl(URL);

        return new ResponseEntity<>(redirectEntity, HttpStatus.CREATED);
    }
}
