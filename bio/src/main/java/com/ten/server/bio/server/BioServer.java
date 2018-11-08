package com.ten.server.bio.server;

import com.ten.server.bio.client.BioClientThread;
import com.ten.server.bio.client.BioClientThreadPoolConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO Server
 *
 * @author wshten
 * @date 2018/11/7
 */
public class BioServer {

    public static void start() {

        // 创建serverSocket对象，监听9000端口
        ServerSocket serverSocket;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(9000);
            socket = serverSocket.accept();

            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BioClientThreadPoolConfig.class);
            BioClientThread service = context.getBean(BioClientThread.class);

            for (int i = 0; i < 10; i++) {
                service.run();
            }
            context.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 监听端口 《阻塞状态》

        BioServerThread bioServerThread = new BioServerThread();
        bioServerThread.run(socket);
    }
}
