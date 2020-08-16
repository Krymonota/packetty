package id.niklas.packetty.common.packet;

import id.niklas.packetty.common.network.AbstractNetworkManager;
import org.apache.logging.log4j.Level;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("unused")
public class PacketManager implements IPacketManager {

    private final Map<Class<? extends IPacket>, CopyOnWriteArrayList<IPacketListener<IPacket>>> packetListeners = new ConcurrentHashMap<>();

    @Override
    public Collection<IPacketListener<IPacket>> getPacketListeners(Class<? extends IPacket> packetClass) {
        final CopyOnWriteArrayList<IPacketListener<IPacket>> packetListeners = this.packetListeners.get(packetClass);

        if (packetListeners == null) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableCollection(packetListeners);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void registerPacketListener(Class<? extends IPacket> packetClass, IPacketListener<? extends IPacket> packetListener) {
        if (!this.packetListeners.containsKey(packetClass)) {
            this.packetListeners.put(packetClass, new CopyOnWriteArrayList<>());
        }

        this.packetListeners.get(packetClass).add((IPacketListener<IPacket>) packetListener);
        AbstractNetworkManager.getLogger().log(Level.DEBUG, "Registered packet listener {} for packet {}", packetListener.getClass().getName(), packetClass.getName());
    }

}
