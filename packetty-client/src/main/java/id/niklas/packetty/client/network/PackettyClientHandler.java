package id.niklas.packetty.client.network;

import id.niklas.packetty.common.network.AbstractNetworkManager;
import id.niklas.packetty.common.network.AbstractPackettyHandler;
import id.niklas.packetty.common.packet.IPacket;
import id.niklas.packetty.common.packet.IPacketManager;
import id.niklas.packetty.common.packet.PacketDirection;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.Level;

@SuppressWarnings("unused")
public class PackettyClientHandler extends AbstractPackettyHandler {

    public PackettyClientHandler(IPacketManager packetManager) {
        super(packetManager);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, IPacket packet) throws Exception {
        if (packet.getPacketDirection().isDirectionForbidden(PacketDirection.TO_CLIENT)) {
            AbstractNetworkManager.getLogger().log(Level.ERROR, "Server with address {} tried to send packet {} which may not be processed by the client.", ctx.channel().remoteAddress(), packet.getClass().getName());

            return;
        }

        super.channelRead0(ctx, packet);
    }

}
