package me.pixelfeft.mixin;

import me.pixelfeft.SeedOre;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
@Inject(method = "render", at = @At("RETURN"))
    private void onRender(RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {
        if (SeedOre.serverSeed == 0) return; // Если сид не введен, ничего не рисуем

        // Здесь мы будем вызывать отрисовку линий (ESP)
        // Для теста пока просто выведем в консоль, что мы нашли точку
        // LOGGER.info("Рисую руду на координатах...");
    }
}
