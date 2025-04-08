package com.iase24.crazy_task_tracker_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class CrazyTaskTrackerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrazyTaskTrackerApiApplication.class, args);
    }

}
