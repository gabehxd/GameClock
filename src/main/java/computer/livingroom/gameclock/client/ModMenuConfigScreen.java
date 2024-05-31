package computer.livingroom.gameclock.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import computer.livingroom.gameclock.client.Config.Position;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.ColorControllerBuilder;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import net.minecraft.text.Text;

import java.awt.*;

import static computer.livingroom.gameclock.client.Config.HANDLER;


public class ModMenuConfigScreen implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> YetAnotherConfigLib.createBuilder()
                .title(Text.literal("Game Clock"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Game Clock"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("UI Options"))
                                .option(Option.<Position>createBuilder()
                                        .name(Text.literal("UI Positioning"))
                                        .description(OptionDescription.of(Text.literal("Position where the time will be drawn")))
                                        .binding(
                                                Position.BOTTOM_LEFT,
                                                () -> HANDLER.instance().guiPosition,
                                                position -> HANDLER.instance().guiPosition = position
                                        )
                                        .controller(positionOption -> EnumControllerBuilder.create(positionOption)
                                                .enumClass(Position.class)
                                                .formatValue(v -> Text.literal(v.name().charAt(0) + v.name().substring(1).toLowerCase().replace('_', ' '))))
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Text.literal("X Padding"))
                                        .description(OptionDescription.of(Text.of("Add some pixel space in between the edge of the screen and the text.")))
                                        .binding(
                                                5,
                                                () -> HANDLER.instance().xPadding,
                                                integer -> HANDLER.instance().xPadding = integer
                                        )
                                        .controller(integerOption -> IntegerSliderControllerBuilder.create(integerOption)
                                                .range(0, 25)
                                                .step(1)
                                                .formatValue(value -> Text.of(value + " px")))
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Text.literal("Y Padding"))
                                        .description(OptionDescription.of(Text.of("Add some pixel space in between the edge of the screen and the text.")))
                                        .binding(
                                                5,
                                                () -> HANDLER.instance().yPadding,
                                                integer -> HANDLER.instance().yPadding = integer
                                        )
                                        .controller(integerOption -> IntegerSliderControllerBuilder.create(integerOption)
                                                .range(0, 25)
                                                .step(1)
                                                .formatValue(value -> Text.of(value + " px")))
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.of("Text Options"))
                                .option(Option.<Color>createBuilder()
                                        .name(Text.literal("Text Color"))
                                        .description(OptionDescription.of(Text.literal("Color of the text drawn on screen.")))
                                        .binding(
                                                new Color(0xFFFFFF),
                                                () -> HANDLER.instance().hexColor,
                                                color -> HANDLER.instance().hexColor = color
                                        )
                                        .controller(opt -> ColorControllerBuilder.create(opt)
                                                .allowAlpha(true))
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.literal("Text Shadow"))
                                        .description(OptionDescription.of(Text.literal("Add a slight shadow to the text.")))
                                        .binding(
                                                true,
                                                () -> HANDLER.instance().shadowText,
                                                shadow -> HANDLER.instance().shadowText = shadow
                                        )
                                        .controller(opt -> BooleanControllerBuilder.create(opt)
                                                .coloured(true))
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.of("24-Hour Clock"))
                                        .description(OptionDescription.of(Text.of("Toggle between using a 24-hour or a 12-hour clock")))
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
