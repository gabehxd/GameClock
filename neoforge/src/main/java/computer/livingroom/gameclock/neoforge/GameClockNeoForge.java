package computer.livingroom.gameclock.neoforge;

import computer.livingroom.gameclock.GameClock;
import computer.livingroom.gameclock.Utils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = GameClock.MOD_ID, dist = Dist.CLIENT)
public final class GameClockNeoForge {
    public GameClockNeoForge(ModContainer container) {
        // Run our common setup.
        container.registerExtensionPoint(IConfigScreenFactory.class, (modContainer, parent) -> Utils.makeConfigScreen(parent));
        GameClock.init();
    }
}
