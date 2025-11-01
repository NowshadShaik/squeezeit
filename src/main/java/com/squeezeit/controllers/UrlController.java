package com.squeezeit.controllers;

import com.squeezeit.entities.RedirectEntity;
import com.squeezeit.services.UrlService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class UrlController {

    private final UrlService urlService;

    @Autowired
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @ResponseBody
    @PostMapping("/squeeze")
    private ResponseEntity<RedirectEntity> shortenUrl(@RequestParam String URL) {

        RedirectEntity redirectEntity = urlService.createShortUrl(URL);

        return new ResponseEntity<>(redirectEntity, HttpStatus.CREATED);
    }

    @GetMapping("/{shortUrlId}")
    private String redirectUrl(@PathVariable("shortUrlId") String shortUrlId) {

        String longUrl = urlService.fetchLongUrl(shortUrlId);

        return "redirect:" + longUrl;
    }
}
