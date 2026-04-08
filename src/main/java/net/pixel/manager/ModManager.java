package net.pixel.manager;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModManager implements ModInitializer {
    public static final String MOD_ID = "cheat-manager";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Cheat Manager успешно запущен!");
        // Здесь позже мы добавим регистрацию кнопок и меню
    }
}
