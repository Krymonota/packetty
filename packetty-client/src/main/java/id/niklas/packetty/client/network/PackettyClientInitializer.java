package id.niklas.packetty.client.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.niklas.packetty.common.network.AbstractPackettyChannelInitializer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.handler.ssl.SslContext;

import java.net.InetSocketAddress;

@SuppressWarnings("unused")
public class PackettyClientInitializer extends AbstractPackettyChannelInitializer {

    private final InetSocketAddress address;

    public PackettyClientInitializer(final InetSocketAddress address, ObjectMapper objectMapper, String packetPackage, ChannelHandler... channelHandlers) {
        super(objectMapper, packetPackage, channelHandlers);
        this.address = address;
    }

    public PackettyClientInitializer(final InetSocketAddress address, SslContext sslContext, ObjectMapper objectMapper, String packetPackage, ChannelHandler... channelHandlers) {
        super(sslContext, objectMapper, packetPackage, channelHandlers);
        this.address = address;
    }

    @Override
    protected void initChannel(Channel channel) {
        super.initChannel(channel);

        final SslContext sslContext = this.getSslContext();

        if (sslContext != null) {
            channel.pipeline().addFirst(sslContext.newHandler(channel.alloc(), this.address.getHostName(), this.address.getPort()));
        }
    }

}
