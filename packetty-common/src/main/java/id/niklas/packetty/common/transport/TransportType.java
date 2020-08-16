package id.niklas.packetty.common.transport;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.*;
import io.netty.channel.kqueue.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ThreadFactory;
import java.util.function.BiFunction;

@RequiredArgsConstructor
@Getter
public enum TransportType {

    // Windows / Default
    NIO(
            "NIO",
            NioServerSocketChannel.class,
            NioSocketChannel.class,
            NioDatagramChannel.class,
            (name, type) -> new NioEventLoopGroup(0, TransportType.createThreadFactory(name, type))
    ),
    // Linux
    EPOLL(
            "Epoll",
            EpollServerSocketChannel.class,
            EpollSocketChannel.class,
            EpollDatagramChannel.class,
            (name, type) -> new EpollEventLoopGroup(0, TransportType.createThreadFactory(name, type))
    ),
    // OSX/macOS
    KQUEUE(
            "KQueue",
            KQueueServerSocketChannel.class,
            KQueueSocketChannel.class,
            KQueueDatagramChannel.class,
            (name, type) -> new KQueueEventLoopGroup(0, TransportType.createThreadFactory(name, type))
    );

    private final String name;
    private final Class<? extends ServerSocketChannel> serverSocketChannelClass;
    private final Class<? extends SocketChannel> socketChannelClass;
    private final Class<? extends DatagramChannel> datagramChannelClass;
    private final BiFunction<String, GroupType, EventLoopGroup> eventLoopGroupFactory;

    public EventLoopGroup createEventLoopGroup(GroupType type) {
        return this.eventLoopGroupFactory.apply(this.name, type);
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public static TransportType getBestTransportType() {
        if (Epoll.isAvailable()) {
            return TransportType.EPOLL;
        } else if (KQueue.isAvailable()) {
            return TransportType.KQUEUE;
        } else {
            return TransportType.NIO;
        }
    }

    private static ThreadFactory createThreadFactory(String name, GroupType type) {
        return new ThreadFactoryBuilder().setNameFormat("Netty " + name + " " + type.toString() + " #%d").setDaemon(true).build();
    }

}
