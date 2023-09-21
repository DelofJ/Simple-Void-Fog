package io.github.delofj.simple_void_fog.mixin;

import io.github.delofj.simple_void_fog.SimpleVoidFog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow @Nullable public ClientLevel level;
    @Shadow @Nullable public LocalPlayer player;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;animateTick(III)V"))
    private void tickInject(CallbackInfo ci) {
        doVoidFogParticles(level, player);
    }

    @Unique
    private void doVoidFogParticles(ClientLevel world, AbstractClientPlayer player) {
        byte var4 = 16;

        for (int var6 = 0; var6 < 1000; ++var6)
        {
            int var7 = Mth.floor(player.getX()) + world.random.nextInt(var4) - world.random.nextInt(var4);
            int var8 = Mth.floor(player.getY()) + world.random.nextInt(var4) - world.random.nextInt(var4);
            int var9 = Mth.floor(player.getZ()) + world.random.nextInt(var4) - world.random.nextInt(var4);
            BlockPos var10 = new BlockPos(var7, var8, var9);
            BlockState var10_ = world.getBlockState(var10);

            if (var10_.getMaterial() == Material.AIR)
            {
                int bedrockLevel = world.dimensionType().minY();
                if (world.random.nextInt(bedrockLevel,bedrockLevel + 8) > var8 && SimpleVoidFog.getWorldHasVoidParticles(world))
                {
                    world.addParticle(ParticleTypes.MYCELIUM, (float)var7 + world.random.nextFloat(), (float)var8 + world.random.nextFloat(), (float)var9 + world.random.nextFloat(), 0.0D, 0.0D, 0.0D);
                }
            }
            else
            {
                var10_.getBlock().animateTick(var10_, world, var10, world.random);
            }
        }
    }
}
