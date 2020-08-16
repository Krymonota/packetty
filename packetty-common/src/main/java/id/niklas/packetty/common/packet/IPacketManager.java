package id.niklas.packetty.common.packet;

import java.util.Collection;

/**
 * The packet manager is used for dealing with packet listeners.
 */
public interface IPacketManager {

    /**
     * Returns a collection of registered packet listeners for the specified packet class
     *
     * @param packetClass The packet class for which registered packet listeners are to be returned
     * @return A collection of registered packet listeners for the specified packet class
     */
    Collection<IPacketListener<IPacket>> getPacketListeners(Class<? extends IPacket> packetClass);

    /**
     * Registers a packet listener for the specified packet class
     *
     * @param packetClass The packet class that is being handled be the packet listener to register
     * @param packetListener The packet listener to register
     */
    @SuppressWarnings("unused")
    void registerPacketListener(Class<? extends IPacket> packetClass, IPacketListener<? extends IPacket> packetListener);

}
