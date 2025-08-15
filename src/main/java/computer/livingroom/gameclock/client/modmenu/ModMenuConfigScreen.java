package computer.livingroom.gameclock.client.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import computer.livingroom.gameclock.client.GameClockClient;
import net.fabricmc.loader.api.FabricLoader;

public class ModMenuConfigScreen implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (!FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
            GameClockClient.LOGGER.error("Configuration unavailable! Please install YetAnotherConfigLib to fix errors!");
            return null;
        }

        return ModScreen::get;
    }
}
