package com.squeezeit.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "redirect_urls")
public class RedirectEntity {

    @Id
    private UUID uuid;

    private String longUrl;

    private String shortUrl;

    private int timesUsed;
}
