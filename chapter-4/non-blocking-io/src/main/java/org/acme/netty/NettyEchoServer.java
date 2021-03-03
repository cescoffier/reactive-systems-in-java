package org.acme.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyEchoServer {

    public static void main(String[] args) throws Exception {
        new NettyEchoServer(9999).run();
    }

    private final int port;

    public NettyEchoServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        // NioEventLoopGroup is a multithreaded event loop that handles I/O operation.
        // The first one, often called 'boss', accepts an incoming connection. The second one, often called 'worker',
        // handles the traffic of the accepted connection once the boss accepts the connection and registers the
        // accepted connection to the worker.
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // ServerBootstrap is a helper class that sets up a server.
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    // the NioServerSocketChannel class is used to instantiate a new Channel to accept incoming connections.
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // this handler is called for each accepted channel and allows customizing the processing.
                        // In this case, we just append the echo handler.
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    });

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync();

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    private static class EchoServerHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            // Write the received object, and flush
            ctx.writeAndFlush(msg);
        }
    }
}
