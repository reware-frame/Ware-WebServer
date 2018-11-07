package com.ten.server.bio.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * BIO Client
 *
 * @author wshten
 * @date 2018/11/7
 */
public class BioClient {
    private static final int THREAD_COUNT = 10;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BioClientThreadPoolConfig.class);
        BioClientThread service = context.getBean(BioClientThread.class);

        for (int i = 0; i < THREAD_COUNT; i++) {
            service.run();
        }
        context.close();
    }
}
