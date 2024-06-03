package computer.livingroom.gameclock;

import dev.architectury.event.events.client.ClientGuiEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

import static computer.livingroom.gameclock.Config.HANDLER;

public final class GameClock {
    public static final String MOD_ID = "gameclock";
    public static final Logger LOGGER = LoggerFactory.getLogger("GameClock");


    public static void init() {
        HANDLER.load();
        ClientGuiEvent.RENDER_HUD.register((graphics, tickDelta) -> {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.getDebugOverlay().showDebugScreen() || minecraft.options.hideGui)
                return;

            Font font = minecraft.font;
            Config cfg = HANDLER.instance();
            String time = cfg.getTime();
            Point point = cfg.getPosition(graphics, time, font);

            graphics.drawString(font, time, point.x, point.y, cfg.getHexColor(), cfg.shadowText);
        });
        LOGGER.info("Initialized");
    }
}
