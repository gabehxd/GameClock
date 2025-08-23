package computer.livingroom.gameclock;

import dev.architectury.event.events.client.ClientGuiEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;

import java.awt.*;

public final class GameClock {
    public static final String MOD_ID = "gameclock";

    public static void init() {
        Config.HANDLER.load();

        ClientGuiEvent.RENDER_HUD.register((graphics, deltaTracker) -> {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.getDebugOverlay().showDebugScreen() || minecraft.options.hideGui)
                return;

            Font font = minecraft.font;
            Config cfg = Config.HANDLER.instance();
            String time = cfg.formatTime();
            if (!cfg.enableTextBackground) {
                Point point = cfg.calculatePosition(graphics, time, font);
                graphics.drawString(font, time, point.x, point.y, Utils.getHexColor(cfg.textColor), cfg.shadowText);
            } else {
                Config.PointCollection collection = cfg.calculatePositions(graphics, time, font);
                graphics.fill(collection.start().x - 1, collection.start().y - 1, collection.end().x + 1, collection.end().y - 1, Utils.getHexColor(cfg.backgroundColor));
                graphics.drawString(font, time, collection.start().x, collection.start().y, Utils.getHexColor(cfg.textColor), cfg.shadowText);
            }
        });
    }
}
