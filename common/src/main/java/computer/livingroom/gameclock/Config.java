package computer.livingroom.gameclock;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;
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
                    .setPath(YACLPlatform.getConfigDir().resolve("gameclock.json5"))
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
    @SerialEntry
    public boolean showSeconds = false;
    @SerialEntry
    boolean leadZero = false;

    private String builtFormat;

    public void buildFormat() {
        StringBuilder formatBuilder = new StringBuilder();
        formatBuilder.append(leadZero ? is24Hour ? "HH" : "hh" : is24Hour ? "H" : "h").append(":mm");

        if (showSeconds) {
            formatBuilder.append(":ss");
        }
        if (!is24Hour) {
            formatBuilder.append(" a");
        }

        builtFormat = formatBuilder.toString();
    }

    public String getTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(builtFormat));
    }

    public Point calculatePosition(GuiGraphics ctx, String str, Font font) {
        int textWidth = font.width(str);
        return switch (this.guiPosition) {
            case TOP_LEFT -> new Point(this.xPadding, this.yPadding);
            case BOTTOM_LEFT -> new Point(this.xPadding, ctx.guiHeight() - font.lineHeight - this.yPadding);
            case TOP_RIGHT -> new Point(ctx.guiWidth() - textWidth - this.xPadding, this.yPadding);
            case BOTTOM_RIGHT ->
                    new Point(ctx.guiWidth() - textWidth - this.xPadding, ctx.guiHeight() - font.lineHeight - this.yPadding);
        };
    }

    public PointCollection calculatePositions(GuiGraphics ctx, String str, Font font) {
        int textWidth = font.width(str);
        return switch (this.guiPosition) {
            case TOP_LEFT -> new PointCollection(
                    new Point(this.xPadding, this.yPadding),
                    new Point(this.xPadding + textWidth, this.yPadding + font.lineHeight)
            );
            case BOTTOM_LEFT -> new PointCollection(
                    new Point(this.xPadding, ctx.guiHeight() - font.lineHeight - this.yPadding),
                    new Point(this.xPadding + textWidth, ctx.guiHeight() - this.yPadding)
            );
            case TOP_RIGHT -> new PointCollection(
                    new Point(ctx.guiWidth() - textWidth - this.xPadding, this.yPadding),
                    new Point(ctx.guiWidth() - this.xPadding, this.yPadding + font.lineHeight)
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
