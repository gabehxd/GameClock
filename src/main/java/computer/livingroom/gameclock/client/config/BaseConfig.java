package computer.livingroom.gameclock.client.config;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseConfig {
    WriteableConfig.Position guiPosition;
    public Boolean shadowText;
    public Color textColor;
    public int xPadding;
    public int yPadding;
    public boolean is24Hour;
    public boolean enableTextBackground;
    public Color backgroundColor;

    public BaseConfig() {
        guiPosition = Position.BOTTOM_LEFT;
        shadowText = true;
        textColor = new Color(0xFFFFFFFF, true);
        xPadding = 5;
        yPadding = 5;
        is24Hour = false;
        enableTextBackground = false;
        backgroundColor = new Color(0x90505050, true);
    }

    public String formatTime() {
        return !this.is24Hour ? LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")) : LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public Point calculatePosition(GuiGraphics ctx, String str, net.minecraft.client.gui.Font font) {
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
