package computer.livingroom.gameclock.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

import static computer.livingroom.gameclock.client.Config.HANDLER;

public class GameClockClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("GameClock");

    @Override
    public void onInitializeClient() {
        HANDLER.load();
        HudRenderCallback.EVENT.register((ctx, tickDelta) -> {
            if (MinecraftClient.getInstance().getDebugHud().shouldShowDebugHud())
                return;

            TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
            Config cfg = HANDLER.instance();
            String time = cfg.getTime();

            Point point = cfg.getPosition(time, cfg.guiPosition, ctx, renderer);
            ctx.drawText(renderer, time, point.x, point.y, cfg.getHexColor(), cfg.shadowText);
        });
        LOGGER.info("Initialized");
    }


}
