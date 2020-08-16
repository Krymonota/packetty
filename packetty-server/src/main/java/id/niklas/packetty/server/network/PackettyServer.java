package id.niklas.packetty.server.network;

import id.niklas.packetty.common.network.AbstractNetworkManager;
import id.niklas.packetty.common.packet.IPacket;
import id.niklas.packetty.common.packet.IPacketManager;
import id.niklas.packetty.common.transport.GroupType;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import lombok.Getter;
import org.apache.logging.log4j.Level;

import java.net.InetSocketAddress;
import java.util.function.Consumer;

@SuppressWarnings("unused")
@Getter
public class PackettyServer extends AbstractNetworkManager implements IPackettyServer {

    private static final WriteBufferWaterMark SERVER_WRITE_MARK = new WriteBufferWaterMark(1 << 21, 1 << 21);

    private final EventLoopGroup bossGroup;
    private final ServerBootstrap serverBootstrap;

    public PackettyServer(IPacketManager packetManager, ChannelHandler channelHandler) {
        super(packetManager, channelHandler);
        this.bossGroup = this.getTransportType().createEventLoopGroup(GroupType.BOSS);
        this.serverBootstrap = new ServerBootstrap();
    }

    @Override
    public void start() {
        super.start();

        this.getServerBootstrap()
                .channel(this.getTransportType().getServerSocketChannelClass())
                .childHandler(this.getChannelHandler())
                .group(this.getBossGroup(), this.getWorkerGroup())
                .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, PackettyServer.SERVER_WRITE_MARK)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.IP_TOS, 0x18)
                .option(ChannelOption.SO_REUSEADDR, true);
    }

    @Override
    public void bind(InetSocketAddress address) {
        this.bind(address, null);
    }

    @Override
    public void bind(InetSocketAddress address, Consumer<Boolean> bindListener) {
        if (this.getServerBootstrap() == null) {
            throw new IllegalStateException("ServerBootstrap has not been initialized yet! Start the server before attempting to bind.");
        }

        this.getServerBootstrap()
                .localAddress(address)
                .bind()
                .addListener((ChannelFutureListener) future -> {
                    if (future.isSuccess()) {
                        this.setChannel(future.channel());
                        AbstractNetworkManager.getLogger().log(Level.INFO, "Successfully bound server to address {}", address.toString());
                    } else {
                        AbstractNetworkManager.getLogger().log(Level.ERROR, "Failed to bind server to address {}", address.toString());
                    }

                    if (bindListener != null) {
                        bindListener.accept(future.isSuccess());
                    }
                }).channel().closeFuture().syncUninterruptibly();
    }

    @Override
    public void sendPacket(Channel channel, IPacket packet) {
        if (!channel.isActive()) {
            AbstractNetworkManager.getLogger().log(Level.WARN, "Packet {} cannot be sent because channel is not active!", packet.getClass().getName());

            return;
        }

        channel.writeAndFlush(packet);
    }

}
