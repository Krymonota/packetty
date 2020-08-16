package id.niklas.packetty.common.transport;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum GroupType {

    BOSS("Boss"),
    WORKER("Worker");

    private final String name;

    @Override
    public String toString() {
        return this.getName();
    }

}
