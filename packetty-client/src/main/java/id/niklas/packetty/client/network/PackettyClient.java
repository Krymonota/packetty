package id.niklas.packetty.client.network;

import id.niklas.packetty.common.network.AbstractNetworkManager;
import id.niklas.packetty.common.packet.IPacket;
import id.niklas.packetty.common.packet.IPacketManager;
import id.niklas.packetty.common.transport.TransportType;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import lombok.Getter;
import org.apache.logging.log4j.Level;

import java.net.InetSocketAddress;
import java.util.function.Consumer;

@SuppressWarnings("unused")
@Getter
public class PackettyClient extends AbstractNetworkManager implements IPackettyClient {

    private final Bootstrap bootstrap;

    public PackettyClient(IPacketManager packetManager, ChannelHandler channelHandler) {
        super(packetManager, channelHandler);
        this.bootstrap = new Bootstrap();
    }

    @Override
    public void start() {
        super.start();

        this.getBootstrap()
                .channel(TransportType.getBestTransportType().getSocketChannelClass())
                .group(this.getWorkerGroup())
                .handler(this.getChannelHandler());
    }

    @Override
    public void connect(InetSocketAddress address) {
        this.connect(address, null);
    }

    @Override
    public void connect(InetSocketAddress address, Consumer<Boolean> connectListener) {
        if (this.getBootstrap() == null) {
            throw new IllegalStateException("Bootstrap has not been initialized yet! Start the client before attempting to connect to a server.");
        }

        this.getBootstrap()
                .connect(address)
                .addListener((ChannelFutureListener) future -> {
                    final Channel channel = future.channel();

                    if (future.isSuccess()) {
                        this.setChannel(channel);
                        AbstractNetworkManager.getLogger().log(Level.INFO, "Connected to server with address {}", address.toString());
                    } else {
                        AbstractNetworkManager.getLogger().log(Level.ERROR, "Failed to connect to server with address {}", address.toString());
                    }

                    if (connectListener != null) {
                        connectListener.accept(future.isSuccess());
                    }
                }).channel().closeFuture().syncUninterruptibly();
    }

    @Override
    public void sendPacket(IPacket packet) {
        final Channel channel = this.getChannel();

        if (channel == null) {
            AbstractNetworkManager.getLogger().log(Level.WARN, "Packet {} cannot be sent because channel has not been initialized yet!", packet.getClass().getName());

            return;
        }

        if (!channel.isActive()) {
            AbstractNetworkManager.getLogger().log(Level.WARN, "Packet {} cannot be sent because channel is not active!", packet.getClass().getName());

            return;
        }

        channel.writeAndFlush(packet);
    }

}
