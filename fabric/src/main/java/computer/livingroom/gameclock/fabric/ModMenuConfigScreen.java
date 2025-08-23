package computer.livingroom.gameclock.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import computer.livingroom.gameclock.Utils;

public class ModMenuConfigScreen implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return Utils::makeConfigScreen;
    }
}
