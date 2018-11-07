package com.ten.server.bio.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * BIO Server
 *
 * @author wshten
 * @date 2018/11/7
 */
public class BioServer {
    private static final int THREAD_COUNT = 10;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BioServerThreadPoolConfig.class);
        BioServerThread service = context.getBean(BioServerThread.class);

        for (int i = 0; i < THREAD_COUNT; i++) {
            service.run();
        }
        context.close();
    }
}
