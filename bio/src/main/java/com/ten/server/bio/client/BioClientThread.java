package com.ten.server.bio.client;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

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
        // 创建各个空对象
        Socket socket = null;
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;

        try (Scanner scanner = new Scanner(System.in)) {
            socket = new Socket("127.0.0.1", 9000);
            // 获取输入输出流
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            String inputLine;

            // 读取数据
            while (true) {
                inputLine = scanner.nextLine();
                if ("end".equals(inputLine)) {
                    break;
                } else {
                    System.out.println("Client：" + inputLine);
                    // 写数据
                    dataOutputStream.writeUTF(inputLine);
                    // 读数据
                    String getLine = dataInputStream.readUTF();
                    System.out.println("Server : " + getLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 先开的流后关闭，后开的流先关闭
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

}
