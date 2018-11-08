package com.ten.server.bio.server;

import com.ten.server.bio.client.BioClientThread;
import com.ten.server.bio.client.BioClientThreadPoolConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

/**
 * BIO Server
 *
 * @author wshten
 * @date 2018/11/7
 */
public class BioServer {

    public static void start() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(9000);
            System.out.println("bio server init");
            Socket socket;
            while (true) {
                socket = server.accept();
                Thread thread = new BioServerThread(socket);
                thread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(server).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
