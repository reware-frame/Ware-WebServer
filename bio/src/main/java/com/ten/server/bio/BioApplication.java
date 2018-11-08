package com.ten.server.bio;

import com.ten.server.bio.client.BioClient;
import com.ten.server.bio.server.BioServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * BIO 同步阻塞 WEB SERVER
 *
 * @author wshten
 * @date 2018/11/8
 */
@SpringBootApplication
public class BioApplication {

    public static void main(String[] args) {
        SpringApplication.run(BioApplication.class, args);

        // 启动Server服务端服务
        BioServer.start();
    }
}
