package com.ten.server.nio;

import com.ten.server.nio.server.NioServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class NioApplication {

    public static void main(String[] args) {
        SpringApplication.run(NioApplication.class, args);

        NioServer server = new NioServer();
        try {
            server.initServer(8000);
            server.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
