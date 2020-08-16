package id.niklas.packetty.server.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.niklas.packetty.common.network.AbstractPackettyChannelInitializer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.handler.ssl.SslContext;

@SuppressWarnings("unused")
public class PackettyServerInitializer extends AbstractPackettyChannelInitializer {

    public PackettyServerInitializer(ObjectMapper objectMapper, String packetPackage, ChannelHandler... channelHandlers) {
        super(objectMapper, packetPackage, channelHandlers);
    }

    public PackettyServerInitializer(SslContext sslContext, ObjectMapper objectMapper, String packetPackage, ChannelHandler... channelHandlers) {
        super(sslContext, objectMapper, packetPackage, channelHandlers);
    }

    @Override
    protected void initChannel(Channel channel) {
        super.initChannel(channel);

        final SslContext sslContext = this.getSslContext();

        if (sslContext != null) {
            channel.pipeline().addFirst(sslContext.newHandler(channel.alloc()));
        }
    }

}
