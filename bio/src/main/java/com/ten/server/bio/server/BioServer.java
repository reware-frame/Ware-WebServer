package com.ten.server.bio.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
    public static void main(String[] args) {
        // 创建各种空对象
        ServerSocket serverSocket = null;
        Socket socket = null;
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream = null;
        try {
            // 创建serverSocket对象，监听9000端口
            serverSocket = new ServerSocket(9000);
            // 监听端口 《阻塞状态》
            socket = serverSocket.accept();
            while (true) {
                // 从流中获取输入源
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                String readLine = null;
                readLine = dataInputStream.readUTF();
                if (readLine == null) {
                    continue;
                }
                if ("end".equals(readLine) || "quit".equals(readLine)) {
                    break;
                }
                System.out.println("Client ：" + readLine);
                // 写数据
                dataOutputStream.writeUTF(readLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭所有流，先开的流后关闭，后开的流先关闭
            if (dataOutputStream != null) {
                try {
                    dataOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dataInputStream != null) {
                try {
                    dataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
