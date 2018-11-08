package com.ten.server.bio;

import com.ten.server.bio.server.BioServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BioApplication {

    public static void main(String[] args) {
        SpringApplication.run(BioApplication.class, args);
        BioServer.start();
    }
}
