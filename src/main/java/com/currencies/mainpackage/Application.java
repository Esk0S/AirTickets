package com.currencies.mainpackage;

import com.impossibl.postgres.api.data.Interval;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import static java.time.temporal.ChronoUnit.SECONDS;

@Log4j2
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        Period per = Period.of(2024, 12, 31);
        Duration dur = Duration.of(123, ChronoUnit.HOURS);
        Interval in = Interval.of(per, dur);
        log.error(dur.get(SECONDS));
    }
}
