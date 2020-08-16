package id.niklas.packetty.common.packet;

import io.netty.channel.Channel;

/**
 * Interface for packet listeners that can be used to execute actions on packet arrival.
 *
 * @param <P> The packet class that is being handled be this packet listener
 */
public interface IPacketListener<P extends IPacket> {

    /**
     * Handles a received packet.
     *
     * @param channel The channel from which the packet has been received
     * @param packet The packet that has been received
     */
    void handle(Channel channel, P packet);

}
