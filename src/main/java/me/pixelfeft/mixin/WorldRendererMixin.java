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

    // В 1.21.10 метод рендеринга называется просто "render"
    @Inject(method = "render", at = @At("RETURN"))
    private void onRender(RenderTickCounter tickCounter, boolean renderBlockOutline, net.minecraft.client.render.Camera camera, 
                          GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, 
                          Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {
        
        // Если сид не введен (0), ничего не делаем, чтобы не грузить твои 6ГБ ОЗУ
        if (SeedOre.serverSeed == 0) return;

        // Тут будет вызов отрисовки наших рамок руды
    }
}
