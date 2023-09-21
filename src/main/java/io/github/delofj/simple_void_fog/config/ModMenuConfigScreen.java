package io.github.delofj.simple_void_fog.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.AlertScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

public class ModMenuConfigScreen implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        boolean isClothConfigLoaded = FabricLoader.getInstance().isModLoaded("cloth-config");
        if (isClothConfigLoaded) {
            return ModConfigInitializer::onModMenuInitialize;
        }
        return this::clothConfigPromptScreen;
    }

    private Screen clothConfigPromptScreen(Screen screen) {
        return new AlertScreen(
                () -> Minecraft.getInstance().setScreen(screen),
                new TranslatableComponent("simple_void_fog.mod_menu.no_cloth_config.title").withStyle(ChatFormatting.RED),
                new TranslatableComponent("simple_void_fog.mod_menu.no_cloth_config.description").withStyle(ChatFormatting.YELLOW)
        );
    }
}



































