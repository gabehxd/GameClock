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
            .id(ResourceLocation.tryBuild("gameclock", "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("gameclock.json5"))
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    public Position guiPosition = Position.BOTTOM_LEFT;
    @SerialEntry
    public Boolean shadowText = true;
    @SerialEntry(value = "hexColor", comment = "I messed up naming it hexcolor in v1 (this is text color)")
    public Color textColor = new Color(0xFFFFFFFF, true);
    @SerialEntry
    public int xPadding = 5;
    @SerialEntry
    public int yPadding = 5;
    @SerialEntry
    public boolean is24Hour = false;
    @SerialEntry
    public boolean enableTextBackground = false;
    @SerialEntry
    public Color backgroundColor = new Color(0x90505050, true);

    public String formatTime() {
        return !this.is24Hour ? LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")) : LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public Point calculatePosition(GuiGraphics ctx, String str, Font font) {
        int textWidth = font.width(str);
        return switch (this.guiPosition) {
            case TOP_LEFT -> new Point(0 + this.xPadding, 0 + this.yPadding);
            case BOTTOM_LEFT -> new Point(0 + this.xPadding, ctx.guiHeight() - font.lineHeight - this.yPadding);
            case TOP_RIGHT -> new Point(ctx.guiWidth() - textWidth - this.xPadding, 0 + this.yPadding);
            case BOTTOM_RIGHT ->
                    new Point(ctx.guiWidth() - textWidth - this.xPadding, ctx.guiHeight() - font.lineHeight - this.yPadding);
        };
    }

    public PointCollection calculatePositions(GuiGraphics ctx, String str, Font font) {
        int textWidth = font.width(str);
        return switch (this.guiPosition) {
            case TOP_LEFT -> new PointCollection(
                    new Point(0 + this.xPadding, 0 + this.yPadding),
                    new Point(0 + this.xPadding + textWidth, 0 + this.yPadding + font.lineHeight)
            );
            case BOTTOM_LEFT -> new PointCollection(
                    new Point(0 + this.xPadding, ctx.guiHeight() - font.lineHeight - this.yPadding),
                    new Point(0 + this.xPadding + textWidth, ctx.guiHeight() - this.yPadding)
            );
            case TOP_RIGHT -> new PointCollection(
                    new Point(ctx.guiWidth() - textWidth - this.xPadding, 0 + this.yPadding),
                    new Point(ctx.guiWidth() - this.xPadding, 0 + this.yPadding + font.lineHeight)
            );
            case BOTTOM_RIGHT -> new PointCollection(
                    new Point(ctx.guiWidth() - textWidth - this.xPadding, ctx.guiHeight() - font.lineHeight - this.yPadding),
                    new Point(ctx.guiWidth() - this.xPadding, ctx.guiHeight() - this.yPadding)
            );
        };
    }

    public enum Position {
        TOP_RIGHT,
        BOTTOM_RIGHT,
        TOP_LEFT,
        BOTTOM_LEFT
    }

    public record PointCollection(Point start, Point end) {
    }
}
