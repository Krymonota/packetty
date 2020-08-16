package id.niklas.packetty.common.network;

import id.niklas.packetty.common.packet.IPacketManager;
import id.niklas.packetty.common.transport.GroupType;
import id.niklas.packetty.common.transport.TransportType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
@Getter
public abstract class AbstractNetworkManager implements INetworkManager {

    @Getter
    private static final Logger logger = LogManager.getLogger("Packetty");
    private final IPacketManager packetManager;
    private final ChannelHandler channelHandler;
    private final TransportType transportType;
    private final EventLoopGroup workerGroup;
    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    private Channel channel;

    public AbstractNetworkManager(IPacketManager packetManager, ChannelHandler channelHandler) {
        this.packetManager = packetManager;
        this.channelHandler = channelHandler;
        this.transportType = TransportType.getBestTransportType();
        this.workerGroup = this.getTransportType().createEventLoopGroup(GroupType.WORKER);
    }

    @Override
    public void start() {
        AbstractNetworkManager.getLogger().log(Level.INFO, "Starting Packetty...");
    }

    @Override
    public void stop() {
        AbstractNetworkManager.getLogger().log(Level.INFO, "Stopping Packetty...");

        if (this.channel != null) {
            this.channel.close().syncUninterruptibly();
        }
    }

}
