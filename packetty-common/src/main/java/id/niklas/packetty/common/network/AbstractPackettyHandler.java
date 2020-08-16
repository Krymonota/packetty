package id.niklas.packetty.common.network;

import id.niklas.packetty.common.packet.IPacket;
import id.niklas.packetty.common.packet.IPacketManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.logging.log4j.Level;

@ChannelHandler.Sharable
public abstract class AbstractPackettyHandler extends SimpleChannelInboundHandler<IPacket> {

    private final IPacketManager packetManager;

    public AbstractPackettyHandler(IPacketManager packetManager) {
        this.packetManager = packetManager;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, IPacket packet) throws Exception {
        this.packetManager.getPacketListeners(packet.getClass()).forEach(packetListener -> packetListener.handle(ctx.channel(), packet));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        AbstractNetworkManager.getLogger().log(Level.ERROR, "Closing connection due to an exception", cause);

        ctx.close();
    }

}
