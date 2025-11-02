package com.squeezeit.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Statistics {

    public ZonedDateTime createdTimestamp;

    public int clickCount;

}
