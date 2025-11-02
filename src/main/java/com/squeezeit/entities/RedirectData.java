package com.squeezeit.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "redirect_urls")
public class RedirectData {

    @Id
    private String shortUrlId;

    private String longUrl;

    private int clickCount;

    private ZonedDateTime createdTimestamp;

    public void incrementClickCount() {
        this.clickCount++;
    }
}
