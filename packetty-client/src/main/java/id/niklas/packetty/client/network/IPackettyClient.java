package id.niklas.packetty.client.network;

import id.niklas.packetty.common.network.INetworkManager;
import id.niklas.packetty.common.packet.IPacket;

import java.net.InetSocketAddress;
import java.util.function.Consumer;

/**
 * Interface that extends {@link INetworkManager} and additionally declares methods that are used by clients.
 */
@SuppressWarnings("unused")
public interface IPackettyClient extends INetworkManager {

    /**
     * Connects to a server.
     *
     * @param address The address to connect to
     */
    void connect(InetSocketAddress address);

    /**
     * Connects to a server and allows passing a consumer that is called when the client has succeeded or failed to connect.
     *
     * @param address The address to connect to
     * @param connectListener The consumer that is called when the client has succeeded or failed to connect
     */
    void connect(InetSocketAddress address, Consumer<Boolean> connectListener);

    /**
     * Sends a packet to the server.
     *
     * @param packet The packet to send
     */
    void sendPacket(IPacket packet);

}
