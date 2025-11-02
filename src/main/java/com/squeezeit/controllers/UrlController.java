package com.squeezeit.controllers;

import com.squeezeit.Exceptions.NoSuchUrlException;
import com.squeezeit.entities.RedirectData;
import com.squeezeit.models.Statistics;
import com.squeezeit.services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class UrlController {

    private final UrlService urlService;

    @Autowired
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/")
    private String home() {
        return "index";
    }

    @PostMapping("/squeeze")
    private String shortenUrl(@RequestParam String longURL, Model model) {

        String shortUrl = urlService.createShortUrl(longURL);
        model.addAttribute("longUrl", longURL);
        model.addAttribute("shortUrl", shortUrl);

        return "index";
    }

    @GetMapping("/{shortUrlId}")
    private String redirectUrl(@PathVariable("shortUrlId") String shortUrlId, Model model) {

        String longUrl = null;
        try {
            longUrl = urlService.fetchLongUrl(shortUrlId);
        } catch (NoSuchUrlException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }

        return "redirect:" + longUrl;
    }

    @GetMapping("/statistics/{shortUrlId}")
    private String statistics(@PathVariable("shortUrlId") String shortUrlId, Model model) {

        Statistics stats = null;

        try {
            stats = urlService.fetchStatistics(shortUrlId);
        } catch (NoSuchUrlException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }

        return "statistics";
    }
}
