package io.github.delofj.simple_void_fog.config;

import io.github.delofj.simple_void_fog.SimpleVoidFog;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class ModConfigInitializer {
    public static ModConfigDefault defaultConfig = new ModConfigDefault();
    public static void onFabricInitialize() {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        ModConfig modConfig = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        SimpleVoidFog.config.enable = modConfig.enable;
        SimpleVoidFog.config.onlyInSurvival = modConfig.onlyInSurvival;
        SimpleVoidFog.config.onlyInOverworld = modConfig.onlyInOverworld;
    }

    public static Screen onModMenuInitialize(Screen screen) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(screen)
                .setTitle(new TranslatableComponent("simple_void_fog.mod_menu.options.title"));

        ConfigCategory mainCategory = builder.getOrCreateCategory(new TextComponent(""));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        mainCategory.addEntry(
                entryBuilder.startBooleanToggle(new TranslatableComponent("simple_void_fog.mod_menu.options.option.enable"), SimpleVoidFog.config.enable)
                        .setDefaultValue(defaultConfig.enable)
                        .setSaveConsumer(newValue -> SimpleVoidFog.config.enable = newValue)
                        .build()
        );
        mainCategory.addEntry(
                entryBuilder.startBooleanToggle(new TranslatableComponent("simple_void_fog.mod_menu.options.option.overworld"), SimpleVoidFog.config.onlyInOverworld)
                        .setDefaultValue(defaultConfig.onlyInOverworld)
                        .setSaveConsumer(newValue -> SimpleVoidFog.config.onlyInOverworld = newValue)
                        .build()
        );
        mainCategory.addEntry(
                entryBuilder.startBooleanToggle(new TranslatableComponent("simple_void_fog.mod_menu.options.option.survival"), SimpleVoidFog.config.onlyInSurvival)
                        .setDefaultValue(defaultConfig.onlyInSurvival)
                        .setSaveConsumer(newValue -> SimpleVoidFog.config.onlyInSurvival = newValue)
                        .build()
        );
        return builder.build();
    }
}
