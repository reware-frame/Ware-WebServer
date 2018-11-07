package com.ten.server.bio.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * BIO Client
 *
 * @author wshten
 * @date 2018/11/7
 */
public class BioClient {
    public static void main(String[] args) {
        // 创建各个空对象
        Socket socket = null;
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        Scanner scanner = new Scanner(System.in);
        try {
            // 创建socket对象
            socket = new Socket("127.0.0.1", 9000);
            // 获取输入输出流
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            String inputLine = null;
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
        } catch (UnknownHostException e) {
            e.printStackTrace();
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
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
