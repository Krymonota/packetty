package id.niklas.packetty.common.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.niklas.packetty.common.packet.IPacket;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.requirementsascode.moonwlker.MoonwlkerModule;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;

public class JacksonPacketEncoder extends MessageToMessageEncoder<IPacket> {

    private final ObjectMapper objectMapper;

    public JacksonPacketEncoder(ObjectMapper objectMapper, String packetPackage) {
        this.objectMapper = objectMapper;

        MoonwlkerModule module =
                MoonwlkerModule.builder()
                        .fromProperty("type").toSubclassesOf(IPacket.class).in(packetPackage)
                        .build();

        this.objectMapper.registerModule(module);
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, IPacket packet, List<Object> list) throws Exception {
        final String json = this.objectMapper.writeValueAsString(packet);

        list.add(ByteBufUtil.encodeString(channelHandlerContext.alloc(), CharBuffer.wrap(json), Charset.defaultCharset()));
    }

}
