package me.pixelfeft;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.server.command.CommandManager.*;
import com.mojang.brigadier.arguments.LongArgumentType;

public class SeedOre implements ModInitializer {
	public static final String MOD_ID = "seedore";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// Здесь мы будем хранить сид сервера
	public static long serverSeed = 0;

	@Override
	public void onInitialize() {
		LOGGER.info("SeedOre: Мод загружен. Готов к поиску руды по сиду!");

		// Регистрируем команду /setseed <число>
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(literal("setseed")
				.then(argument("seed", LongArgumentType.longArg())
					.executes(context -> {
						serverSeed = LongArgumentType.getLong(context, "seed");
						context.getSource().sendFeedback(() -> Text.literal("Сид установлен: " + serverSeed), false);
						return 1;
					})));
		});
	}
}
