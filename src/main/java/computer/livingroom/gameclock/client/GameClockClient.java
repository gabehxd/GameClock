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
            if (minecraft.options.renderDebug || minecraft.options.hideGui)
                return;

            Font font = minecraft.font;
            Config cfg = HANDLER.instance();
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
        LOGGER.info("Initialized");
    }
}
