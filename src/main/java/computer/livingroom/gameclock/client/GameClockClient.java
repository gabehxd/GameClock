package computer.livingroom.gameclock.client;

import computer.livingroom.gameclock.client.config.BaseConfig;
import computer.livingroom.gameclock.client.config.WriteableConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class GameClockClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("GameClock");

    public static BaseConfig cfg;

    @Override
    public void onInitializeClient() {
        if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
            WriteableConfig.HANDLER.load();
            cfg = WriteableConfig.HANDLER.instance();
        } else {
            cfg = new BaseConfig();
        }

        //TODO: For some reason if cfg is baseconfig it will only ever use default vars
        HudElementRegistry.addFirst(ResourceLocation.fromNamespaceAndPath("gameclock", "hud"), (graphics, tickCounter) -> {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.getDebugOverlay().showDebugScreen() || minecraft.options.hideGui)
                return;

            Font font = minecraft.font;
            String time = cfg.formatTime();
            if (!cfg.enableTextBackground) {
                Point point = cfg.calculatePosition(graphics, time, font);
                graphics.drawString(font, time, point.x, point.y, Utils.getHexColor(cfg.textColor), cfg.shadowText);
            } else {
                WriteableConfig.PointCollection collection = cfg.calculatePositions(graphics, time, font);
                graphics.fill(collection.start().x - 1, collection.start().y - 1, collection.end().x + 1, collection.end().y - 1, Utils.getHexColor(cfg.backgroundColor));
                graphics.drawString(font, time, collection.start().x, collection.start().y, Utils.getHexColor(cfg.textColor), cfg.shadowText);
            }
        });
        LOGGER.info("Initialized");
    }
}
