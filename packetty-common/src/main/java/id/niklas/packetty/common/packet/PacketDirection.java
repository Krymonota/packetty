package id.niklas.packetty.common.packet;

/**
 * Enum defining the direction(s) in which a packet may be sent.
 */
public enum PacketDirection {

    TO_SERVER,
    TO_CLIENT,
    BOTH;

    /**
     * Returns true if the direction is forbidden.
     *
     * @param packetReceiver The direction of the packet receiver
     * @return true if the direction is forbidden
     */
    public boolean isDirectionForbidden(PacketDirection packetReceiver) {
        switch (this) {
            case BOTH -> {
                return false;
            }
            case TO_SERVER -> {
                return packetReceiver != PacketDirection.TO_SERVER;
            }
            case TO_CLIENT -> {
                return packetReceiver != PacketDirection.TO_CLIENT;
            }
            default -> throw new IllegalStateException("Unhandled PacketDirection!");
        }
    }

}
