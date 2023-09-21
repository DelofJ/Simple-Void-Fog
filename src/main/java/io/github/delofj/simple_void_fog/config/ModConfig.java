package io.github.delofj.simple_void_fog.config;

import io.github.delofj.simple_void_fog.SimpleVoidFog;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = SimpleVoidFog.MOD_ID)
public class ModConfig implements ConfigData {
    @Comment("Enable the mod")
    public boolean enable = ModConfigInitializer.defaultConfig.enable;

    @Comment("Only In Overworld")
    public boolean onlyInOverworld = ModConfigInitializer.defaultConfig.onlyInOverworld;

    @Comment("Only in survival")
    public boolean onlyInSurvival = ModConfigInitializer.defaultConfig.onlyInSurvival;
}
