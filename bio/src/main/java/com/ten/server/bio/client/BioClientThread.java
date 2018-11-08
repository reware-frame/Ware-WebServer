package com.ten.server.bio.client;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Client线程
 *
 * @author wshten
 * @date 2018/11/7
 */
@Service
public class BioClientThread {

    @Async
    public void run() {
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream = null;
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 9000);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());

            // 写数据
            String data = "随机产生数据:" + Math.random();
            System.out.println("Client：" + data);
            dataOutputStream.writeUTF(data);
            // 读数据
            String getLine = dataInputStream.readUTF();
            System.out.println("Server : " + getLine);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            shutdown(dataInputStream, dataOutputStream, socket);
        }
    }

    /**
     * 先开的流后关闭，后开的流先关闭
     */
    private void shutdown(DataInputStream dataInputStream, DataOutputStream dataOutputStream, Socket socket) {
        if (dataInputStream != null) {
            try {
                dataInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (dataOutputStream != null) {
            try {
                dataOutputStream.close();
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
    }
}
