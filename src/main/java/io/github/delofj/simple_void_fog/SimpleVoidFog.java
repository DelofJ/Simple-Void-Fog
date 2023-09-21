package io.github.delofj.simple_void_fog;

import io.github.delofj.simple_void_fog.config.ModConfigDefault;
import io.github.delofj.simple_void_fog.config.ModConfigInitializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.FogType;

public class SimpleVoidFog implements ClientModInitializer {
    public static final String MOD_ID = "simple_void_fog";
    public static ModConfigDefault config;

    @Override
    public void onInitializeClient() {
        boolean isClothConfigLoaded = FabricLoader.getInstance().isModLoaded("cloth-config");
        config = new ModConfigDefault();
        if (isClothConfigLoaded) {
            ModConfigInitializer.onFabricInitialize();
        }
    }

    public static boolean getWorldHasVoidParticles(ClientLevel world) {
        if (!config.enable) { return false; }
        if (config.onlyInOverworld) {
            return !world.getLevelData().isFlat && world.dimensionType().hasSkyLight();
        }
        return !world.getLevelData().isFlat;
    }

    public static boolean canRenderDepthFog(Camera camera, Entity entity) {
        if (!(entity instanceof AbstractClientPlayer clientPlayer)) { return false; }
        if (!SimpleVoidFog.getWorldHasVoidParticles(clientPlayer.clientLevel)) { return false; }
        if (config.onlyInSurvival) {
            if (clientPlayer.isSpectator() || clientPlayer.isCreative()) { return false; }
        }

        if (clientPlayer.hasEffect(MobEffects.BLINDNESS)) { return false; }
        if (!(camera.getFluidInCamera() == FogType.NONE)) { return false; }

        return true;
    }

}
