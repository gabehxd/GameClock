package computer.livingroom.gameclock;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.ColorControllerBuilder;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.awt.*;

public class Utils {
    public static int getHexColor(Color color) {
        //What the flip java
        return (color.getAlpha() << 24) | (color.getRed() << 16) | (color.getGreen() << 8) | color.getBlue();
    }

    public static Screen makeConfigScreen(Screen parent) {
        Option<Color> bgColor = Option.<Color>createBuilder()
                .name(Component.literal("Background Color"))
                .description(OptionDescription.of(Component.literal("Color of the background drawn on screen.")))
                .binding(
                        new Color(0x90505050),
                        () -> Config.HANDLER.instance().backgroundColor,
                        color -> Config.HANDLER.instance().backgroundColor = color
                )
                .controller(opt -> ColorControllerBuilder.create(opt)
                        .allowAlpha(true))
                .build();

        return YetAnotherConfigLib.createBuilder()
                .title(net.minecraft.network.chat.Component.literal("Game Clock"))
                .category(ConfigCategory.createBuilder()
                        .name(net.minecraft.network.chat.Component.literal("Game Clock"))
                        .group(OptionGroup.createBuilder()
                                .name(net.minecraft.network.chat.Component.literal("UI Options"))
                                .option(Option.<Config.Position>createBuilder()
                                        .name(net.minecraft.network.chat.Component.literal("UI Positioning"))
                                        .description(OptionDescription.of(net.minecraft.network.chat.Component.literal("Position where the time will be drawn")))
                                        .binding(
                                                Config.Position.BOTTOM_LEFT,
                                                () -> Config.HANDLER.instance().guiPosition,
                                                position -> Config.HANDLER.instance().guiPosition = position
                                        )
                                        .controller(positionOption -> EnumControllerBuilder.create(positionOption)
                                                .enumClass(Config.Position.class)
                                                .formatValue(v -> net.minecraft.network.chat.Component.literal(v.name().charAt(0) + v.name().substring(1).toLowerCase().replace('_', ' '))))
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(net.minecraft.network.chat.Component.literal("X Padding"))
                                        .description(OptionDescription.of(net.minecraft.network.chat.Component.literal("Add some pixel space in between the edge of the screen and the text.")))
                                        .binding(
                                                5,
                                                () -> Config.HANDLER.instance().xPadding,
                                                integer -> Config.HANDLER.instance().xPadding = integer
                                        )
                                        .controller(integerOption -> IntegerSliderControllerBuilder.create(integerOption)
                                                .range(0, 25)
                                                .step(1)
                                                .formatValue(value -> net.minecraft.network.chat.Component.literal(value + " px")))
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(net.minecraft.network.chat.Component.literal("Y Padding"))
                                        .description(OptionDescription.of(net.minecraft.network.chat.Component.literal("Add some pixel space in between the edge of the screen and the text.")))
                                        .binding(
                                                5,
                                                () -> Config.HANDLER.instance().yPadding,
                                                integer -> Config.HANDLER.instance().yPadding = integer
                                        )
                                        .controller(integerOption -> IntegerSliderControllerBuilder.create(integerOption)
                                                .range(0, 25)
                                                .step(1)
                                                .formatValue(value -> net.minecraft.network.chat.Component.literal(value + " px")))
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(net.minecraft.network.chat.Component.literal("Text Options"))
                                .option(Option.<Color>createBuilder()
                                        .name(net.minecraft.network.chat.Component.literal("Text Color"))
                                        .description(OptionDescription.of(net.minecraft.network.chat.Component.literal("Color of the text drawn on screen.")))
                                        .binding(
                                                new Color(0xFFFFFF),
                                                () -> Config.HANDLER.instance().textColor,
                                                color -> Config.HANDLER.instance().textColor = color
                                        )
                                        .controller(opt -> ColorControllerBuilder.create(opt)
                                                .allowAlpha(true))
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(net.minecraft.network.chat.Component.literal("Text Shadow"))
                                        .description(OptionDescription.of(net.minecraft.network.chat.Component.literal("Add a slight shadow to the text.")))
                                        .binding(
                                                true,
                                                () -> Config.HANDLER.instance().shadowText,
                                                shadow -> Config.HANDLER.instance().shadowText = shadow
                                        )
                                        .controller(opt -> BooleanControllerBuilder.create(opt)
                                                .coloured(true))
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(net.minecraft.network.chat.Component.literal("24-Hour Clock"))
                                        .description(OptionDescription.of(net.minecraft.network.chat.Component.literal("Toggle between using a 24-hour or a 12-hour clock")))
                                        .binding(
                                                false,
                                                () -> Config.HANDLER.instance().is24Hour,
                                                bool -> Config.HANDLER.instance().is24Hour = bool
                                        )
                                        .controller(BooleanControllerBuilder::create)
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(net.minecraft.network.chat.Component.literal("Background Options"))
                                .option(Option.<Boolean>createBuilder()
                                        .name(net.minecraft.network.chat.Component.literal("Text Background"))
                                        .description(OptionDescription.of(Component.literal("Add a background to the clock")))
                                        .binding(
                                                false,
                                                () -> Config.HANDLER.instance().enableTextBackground,
                                                bool -> Config.HANDLER.instance().enableTextBackground = bool
                                        )
                                        .controller(BooleanControllerBuilder::create)
                                        .addListener((option, event) -> bgColor.setAvailable(option.pendingValue()))
                                        .build())
                                .option(bgColor)
                                .build())
                        .build())
                .save(() -> Config.HANDLER.save())
                .build().generateScreen(parent);
    }
}
