package id.niklas.packetty.common.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.niklas.packetty.common.codec.JacksonPacketDecoder;
import id.niklas.packetty.common.codec.JacksonPacketEncoder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.ssl.SslContext;
import lombok.Getter;

public abstract class AbstractPackettyChannelInitializer extends ChannelInitializer<Channel> {

    @Getter
    private final SslContext sslContext;
    private final ObjectMapper objectMapper;
    private final String packetPackage;
    private final ChannelHandler[] channelHandlers;

    protected AbstractPackettyChannelInitializer(ObjectMapper objectMapper, String packetPackage, ChannelHandler... channelHandlers) {
        this(null, objectMapper, packetPackage, channelHandlers);
    }

    protected AbstractPackettyChannelInitializer(SslContext sslContext, ObjectMapper objectMapper, String packetPackage, ChannelHandler... channelHandlers) {
        this.sslContext = sslContext;
        this.objectMapper = objectMapper;
        this.packetPackage = packetPackage;
        this.channelHandlers = channelHandlers;
    }

    @Override
    protected void initChannel(Channel channel) {
        channel.pipeline().addLast(new LengthFieldPrepender(4));
        channel.pipeline().addLast(new JacksonPacketEncoder(objectMapper, packetPackage));
        channel.pipeline().addLast(new LengthFieldBasedFrameDecoder(0x100000, 0, 4, 0, 4));
        channel.pipeline().addLast(new JacksonPacketDecoder(objectMapper, packetPackage));
        channel.pipeline().addLast(this.channelHandlers);
    }

}
