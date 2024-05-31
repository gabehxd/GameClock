package computer.livingroom.gameclock.client;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Config {
    public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(new ResourceLocation("gameclock", "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("gameclock.json5"))
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    public Position guiPosition = Position.BOTTOM_LEFT;
    @SerialEntry
    public Boolean shadowText = true;
    @SerialEntry
    public Color hexColor = new Color(0xFFFFFFFF, true);
    @SerialEntry
    public int xPadding = 5;
    @SerialEntry
    public int yPadding = 5;
    @SerialEntry
    public boolean is24Hour = false;

    public int getHexColor() {
        //What the fuck java
        return (hexColor.getAlpha() << 24) | (hexColor.getRed() << 16) | (hexColor.getGreen() << 8) | hexColor.getBlue();
    }

    public String getTime() {
        return !this.is24Hour ? LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")) : LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public Point getPosition(GuiGraphics ctx, String str, Font font) {
        return switch (this.guiPosition) {
            case TOP_LEFT -> new Point(0 + this.xPadding, 0 + this.yPadding);
            case BOTTOM_LEFT ->
                    new Point(0 + this.xPadding, ctx.guiHeight() - font.lineHeight - this.yPadding);
            case TOP_RIGHT ->
                    new Point(ctx.guiWidth() - font.width(str) - this.xPadding, 0 + this.yPadding);
            case BOTTOM_RIGHT ->
                    new Point(ctx.guiWidth() - font.width(str) - this.xPadding, ctx.guiHeight() - font.lineHeight - this.yPadding);
        };
    }

    public enum Position {
        TOP_RIGHT,
        BOTTOM_RIGHT,
        TOP_LEFT,
        BOTTOM_LEFT
    }
}
