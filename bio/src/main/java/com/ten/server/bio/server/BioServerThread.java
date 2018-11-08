package com.ten.server.bio.server;

import org.springframework.stereotype.Service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Server线程
 *
 * @author wshten
 * @date 2018/11/7
 */
public class BioServerThread extends Thread {

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public BioServerThread() {
    }

    public BioServerThread(Socket socket) {
        this.socket = socket;
    }

    /**
     * Socket数据操作
     */
    @Override
    public void run() {
        try {
            while (true) {
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                String readLine = dataInputStream.readUTF();
                if ("end".equals(readLine) || "quit".equals(readLine)) {
                    break;
                }
                System.out.println("Client Data is ：" + readLine);
                dataOutputStream.writeUTF(readLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            shutdown();
        }
    }

    /**
     * 关闭所有流，先开的流后关闭，后开的流先关闭
     */
    private void shutdown() {
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
    }
}
