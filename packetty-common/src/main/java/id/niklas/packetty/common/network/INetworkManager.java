package id.niklas.packetty.common.network;

import id.niklas.packetty.common.packet.IPacketManager;

/**
 * Basic interface that declares methods that are used both by clients and servers.
 */
@SuppressWarnings("unused")
public interface INetworkManager {

    /**
     * Is called when the application is being started.
     */
    void start();

    /**
     * Is called when the application is being stopped.
     */
    void stop();

    /**
     * Returns the packet manager that is being used by the application.
     * The packet manager allows registering and getting packet listeners.
     *
     * @return An instance of a packet manager, usually {@link id.niklas.packetty.common.packet.PacketManager}
     */
    IPacketManager getPacketManager();

}
