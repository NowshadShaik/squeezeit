package com.squeezeit.controller;

import com.squeezeit.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/squeezeit")
public class UrlController {

    private UrlService urlservice;

    @Autowired
    public UrlController(UrlService urlservice) {
        this.urlservice = urlservice;
    }

    @PostMapping("/squeeze")
    private ResponseEntity<String> squeezeUrl(@RequestParam String URL) {

        String shortUrl = urlservice.shorten(URL);

        return new ResponseEntity<>(shortUrl, HttpStatus.CREATED);
    }
}
