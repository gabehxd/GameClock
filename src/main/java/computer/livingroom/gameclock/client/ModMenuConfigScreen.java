package computer.livingroom.gameclock.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import computer.livingroom.gameclock.client.Config.Position;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.ColorControllerBuilder;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import java.awt.*;
import net.minecraft.network.chat.Component;

import static computer.livingroom.gameclock.client.Config.HANDLER;


public class ModMenuConfigScreen implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> YetAnotherConfigLib.createBuilder()
                .title(Component.literal("Game Clock"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.literal("Game Clock"))
                        .group(OptionGroup.createBuilder()
                                .name(Component.literal("UI Options"))
                                .option(Option.<Position>createBuilder()
                                        .name(Component.literal("UI Positioning"))
                                        .description(OptionDescription.of(Component.literal("Position where the time will be drawn")))
                                        .binding(
                                                Position.BOTTOM_LEFT,
                                                () -> HANDLER.instance().guiPosition,
                                                position -> HANDLER.instance().guiPosition = position
                                        )
                                        .controller(positionOption -> EnumControllerBuilder.create(positionOption)
                                                .enumClass(Position.class)
                                                .formatValue(v -> Component.literal(v.name().charAt(0) + v.name().substring(1).toLowerCase().replace('_', ' '))))
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.literal("X Padding"))
                                        .description(OptionDescription.of(Component.literal("Add some pixel space in between the edge of the screen and the text.")))
                                        .binding(
                                                5,
                                                () -> HANDLER.instance().xPadding,
                                                integer -> HANDLER.instance().xPadding = integer
                                        )
                                        .controller(integerOption -> IntegerSliderControllerBuilder.create(integerOption)
                                                .range(0, 25)
                                                .step(1)
                                                .formatValue(value -> Component.literal(value + " px")))
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.literal("Y Padding"))
                                        .description(OptionDescription.of(Component.literal("Add some pixel space in between the edge of the screen and the text.")))
                                        .binding(
                                                5,
                                                () -> HANDLER.instance().yPadding,
                                                integer -> HANDLER.instance().yPadding = integer
                                        )
                                        .controller(integerOption -> IntegerSliderControllerBuilder.create(integerOption)
                                                .range(0, 25)
                                                .step(1)
                                                .formatValue(value -> Component.literal(value + " px")))
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Component.literal("Text Options"))
                                .option(Option.<Color>createBuilder()
                                        .name(Component.literal("Text Color"))
                                        .description(OptionDescription.of(Component.literal("Color of the text drawn on screen.")))
                                        .binding(
                                                new Color(0xFFFFFF),
                                                () -> HANDLER.instance().hexColor,
                                                color -> HANDLER.instance().hexColor = color
                                        )
                                        .controller(opt -> ColorControllerBuilder.create(opt)
                                                .allowAlpha(true))
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.literal("Text Shadow"))
                                        .description(OptionDescription.of(Component.literal("Add a slight shadow to the text.")))
                                        .binding(
                                                true,
                                                () -> HANDLER.instance().shadowText,
                                                shadow -> HANDLER.instance().shadowText = shadow
                                        )
                                        .controller(opt -> BooleanControllerBuilder.create(opt)
                                                .coloured(true))
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.literal("24-Hour Clock"))
                                        .description(OptionDescription.of(Component.literal("Toggle between using a 24-hour or a 12-hour clock")))
                                        .binding(
                                                false,
                                                () -> HANDLER.instance().is24Hour,
                                                bool -> HANDLER.instance().is24Hour = bool
                                        )
                                        .controller(BooleanControllerBuilder::create)
                                        .build())
                                .build())
                        .build())
                .save(() -> HANDLER.save())
                .build()
                .generateScreen(parent);
    }
}
