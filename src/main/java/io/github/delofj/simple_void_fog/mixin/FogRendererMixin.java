package io.github.delofj.simple_void_fog.mixin;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.delofj.simple_void_fog.SimpleVoidFog;
import net.minecraft.client.Camera;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FogRenderer.class)
public abstract class FogRendererMixin {
    @Inject(method = "setupFog", at = @At(value = "RETURN"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void applyFogInject(Camera camera, FogRenderer.FogMode fogMode, float renderDistance, boolean thickFog, CallbackInfo ci, FogType fogType, Entity entity, FogShape fogShape, float g, float h) {
        if (!SimpleVoidFog.canRenderDepthFog(camera, entity)) { return; }

        AbstractClientPlayer clientPlayerEntity = (AbstractClientPlayer) entity ;
        double var10 = (double)((((int)clientPlayerEntity.getBrightness()) & 15728640) >> 20) / 16.0D + (((float)camera.getPosition().y - (float)clientPlayerEntity.clientLevel.getMinBuildHeight()) + 4.0D) / 32.0D;
        if (var10 < 1.0D)
        {
            if (var10 < 0.0D)
            {
                var10 = 0.0D;
            }

            var10 *= var10;
            float var9 = 100.0F * (float)var10;

            if (var9 < 5.0F)
            {
                var9 = 5.0F;
            }

            if (renderDistance > var9)
            {
                renderDistance = var9;
            }
        }

        if (thickFog) {
            g = renderDistance * 0.05F;
            h = Math.min(renderDistance, 192.0F) * 0.5F;
        } else if (fogMode == FogRenderer.FogMode.FOG_SKY) {
            g = 0.0F;
            h = renderDistance;
            fogShape = FogShape.CYLINDER;
        } else {
            float k = Mth.clamp(renderDistance / 10.0F, 4.0F, 64.0F);
            g = renderDistance - k;
            h = renderDistance;
            fogShape = FogShape.CYLINDER;
        }

        RenderSystem.setShaderFogStart(g);
        RenderSystem.setShaderFogEnd(h);
        RenderSystem.setShaderFogShape(fogShape);
    }
}
