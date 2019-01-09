package com.ten.server.nio.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class NettyServer {
    private final int port;

    public NettyServer(int port) {
        this.port = port;
    }

    /**
     * 引导服务器启动
     */
    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1) {
            System.err.println("Usage: " + NettyServer.class.getSimpleName() + " <port>");
            return;
        }
        int port = Integer.parseInt(args[0]);
        new NettyServer(port).start();
    }

    private void start() throws InterruptedException {
        final NettyServerHandler serverHandler = new NettyServerHandler();
        // 创建EventGroup进行事件的处理
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 创建ServerBootstrap引导和绑定服务器
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    // 指定所使用的NIO传输Channel
                    .channel(NioServerSocketChannel.class)
                    // 使用指定的端口设置套接字地址
                    .localAddress(new InetSocketAddress(port))
                    // 添加一个ServerHandler到子Channel的ChannelPipeline
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        /**
                         * 当一个新的连接被接受时，一个新的子Channel将会被创建，将Handler实例添加到该Channel的ChannelPipeline中
                         */
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            // ServerHandler被标注为@Sharable，所以我们可以总是使用同样的实例
                            socketChannel.pipeline().addLast(serverHandler);
                        }
                    });
            // 异步绑定服务器；调用sync()方法阻塞等待直到绑定完成
            ChannelFuture f = b.bind().sync();
            // 获取Channel的CloseFuture，并且阻塞当前线程直到它完成
            f.channel().closeFuture().sync();
        } finally {
            // 关闭EventLoopGroup释放所有的资源
            group.shutdownGracefully().sync();
        }
    }

}
