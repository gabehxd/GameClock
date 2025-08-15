package computer.livingroom.gameclock.client.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class WriteableConfig extends BaseConfig {
    public static ConfigClassHandler<WriteableConfig> HANDLER = ConfigClassHandler.createBuilder(WriteableConfig.class)
            .id(ResourceLocation.tryBuild("gameclock", "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("gameclock.json5"))

                    .setJson5(true)
                    .build())
            .build();

    public WriteableConfig() {
        super();

    }


    @SerialEntry
    public Position guiPosition;
    @SerialEntry
    public Boolean shadowText;
    @SerialEntry(value = "hexColor", comment = "I messed up naming it hexcolor in v1 (this is text color)")
    public Color textColor;
    @SerialEntry
    public int xPadding;
    @SerialEntry
    public int yPadding;
    @SerialEntry
    public boolean is24Hour;
    @SerialEntry
    public boolean enableTextBackground;
    @SerialEntry
    public Color backgroundColor;
}
