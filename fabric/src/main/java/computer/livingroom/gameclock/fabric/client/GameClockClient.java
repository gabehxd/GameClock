package computer.livingroom.gameclock.fabric.client;

import net.fabricmc.api.ClientModInitializer;

public final class GameClockClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        computer.livingroom.gameclock.GameClock.init();
    }
}
