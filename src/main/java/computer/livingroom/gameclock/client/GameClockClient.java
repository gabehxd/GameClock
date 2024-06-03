package computer.livingroom.gameclock.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

import static computer.livingroom.gameclock.client.Config.HANDLER;

public class GameClockClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("GameClock");

    @Override
    public void onInitializeClient() {
        HANDLER.load();
        HudRenderCallback.EVENT.register((graphics, tickDelta) -> {
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
