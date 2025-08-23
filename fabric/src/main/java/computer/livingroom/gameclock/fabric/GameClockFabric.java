package computer.livingroom.gameclock.fabric;

import computer.livingroom.gameclock.GameClock;
import net.fabricmc.api.ClientModInitializer;

public final class GameClockFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        GameClock.init();
    }
}
