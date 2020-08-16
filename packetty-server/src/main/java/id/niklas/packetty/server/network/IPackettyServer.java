package id.niklas.packetty.server.network;

import id.niklas.packetty.common.network.INetworkManager;
import id.niklas.packetty.common.packet.IPacket;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.function.Consumer;

/**
 * Interface that extends {@link INetworkManager} and additionally declares methods that are used by servers.
 */
@SuppressWarnings("unused")
public interface IPackettyServer extends INetworkManager {

    /**
     * Binds the server to an address.
     *
     * @param address The address to bind the server to
     */
    void bind(InetSocketAddress address);

    /**
     * Binds the server to an address and allows passing a consumer that is called when the server has succeeded or failed to bind.
     *
     * @param address The address to bind the server to
     * @param bindListener The consumer that is called when the server has succeeded or failed to bind
     */
    void bind(InetSocketAddress address, Consumer<Boolean> bindListener);

    /**
     * Sends a packet to a client.
     *
     * @param channel The channel to send the packet to
     * @param packet The packet to send
     */
    void sendPacket(Channel channel, IPacket packet);

}
