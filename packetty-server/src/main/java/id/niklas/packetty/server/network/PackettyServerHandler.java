package id.niklas.packetty.server.network;

import id.niklas.packetty.common.network.AbstractNetworkManager;
import id.niklas.packetty.common.network.AbstractPackettyHandler;
import id.niklas.packetty.common.packet.IPacket;
import id.niklas.packetty.common.packet.IPacketManager;
import id.niklas.packetty.common.packet.PacketDirection;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.Level;

@SuppressWarnings("unused")
public class PackettyServerHandler extends AbstractPackettyHandler {

    public PackettyServerHandler(IPacketManager packetManager) {
        super(packetManager);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, IPacket packet) throws Exception {
        if (packet.getPacketDirection().isDirectionForbidden(PacketDirection.TO_SERVER)) {
            AbstractNetworkManager.getLogger().log(Level.ERROR, "Client with address {} tried to send packet {} which may not be processed by the server.", ctx.channel().remoteAddress(), packet.getClass().getName());

            return;
        }

        super.channelRead0(ctx, packet);
    }

}
