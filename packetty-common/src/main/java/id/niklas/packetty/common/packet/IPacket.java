package id.niklas.packetty.common.packet;

/**
 * Interface that defines a class to a packet.
 */
public interface IPacket {

    /**
     * Returns the direction(s) in which the packet may be sent.
     *
     * @return The direction(s) in which the packet may be sent. Must be either {@link PacketDirection#BOTH}, {@link PacketDirection#TO_SERVER} or {@link PacketDirection#TO_CLIENT}.
     */
    PacketDirection getPacketDirection();

}
