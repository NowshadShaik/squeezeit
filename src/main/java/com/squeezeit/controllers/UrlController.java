package com.squeezeit.controllers;

import com.squeezeit.Exceptions.NoSuchUrlException;
import com.squeezeit.entities.RedirectData;
import com.squeezeit.models.Statistics;
import com.squeezeit.services.UrlService;
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
    private ResponseEntity<RedirectData> shortenUrl(@RequestParam String URL) {

        RedirectData redirectData = urlService.createShortUrl(URL);

        return new ResponseEntity<>(redirectData, HttpStatus.CREATED);
    }

    @GetMapping("/{shortUrlId}")
    private String redirectUrl(@PathVariable("shortUrlId") String shortUrlId) {

        String longUrl = null;
        try {
            longUrl = urlService.fetchLongUrl(shortUrlId);
        } catch (NoSuchUrlException e) {
            return "redirect:/errorPage";
        }

        return "redirect:" + longUrl;
    }

    @GetMapping("/statistics/{shortUrlId}")
    private ResponseEntity<?> statistics(@PathVariable("shortUrlId") String shortUrlId) {

        Statistics stats = null;

        try {
            stats = urlService.fetchStatistics(shortUrlId);
        } catch (NoSuchUrlException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}
