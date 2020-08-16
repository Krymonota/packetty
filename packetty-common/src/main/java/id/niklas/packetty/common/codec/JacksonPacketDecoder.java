package id.niklas.packetty.common.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.niklas.packetty.common.packet.IPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.requirementsascode.moonwlker.MoonwlkerModule;

import java.nio.charset.Charset;
import java.util.List;

public class JacksonPacketDecoder extends MessageToMessageDecoder<ByteBuf> {

    private final ObjectMapper objectMapper;

    public JacksonPacketDecoder(ObjectMapper objectMapper, String packetPackage) {
        this.objectMapper = objectMapper;

        MoonwlkerModule module =
                MoonwlkerModule.builder()
                        .fromProperty("type").toSubclassesOf(IPacket.class).in(packetPackage)
                        .build();

        this.objectMapper.registerModule(module);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(this.objectMapper.readValue(byteBuf.toString(Charset.defaultCharset()), IPacket.class));
    }

}
