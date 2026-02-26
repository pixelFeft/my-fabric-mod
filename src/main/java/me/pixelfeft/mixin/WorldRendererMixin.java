package me.pixelfeft.mixin;

import me.pixelfeft.SeedOre;
import me.pixelfeft.SeedCalculator;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.List;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Inject(method = "render", at = @At("RETURN"))
    private void onRender(RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, 
                          GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, 
                          Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {
        
        // Проверка сида
        if (SeedOre.serverSeed == 0) return;

        // Позиция игрока
        Vec3d cameraPos = camera.getPos();
        int chunkX = (int) cameraPos.x >> 4;
        int chunkZ = (int) cameraPos.z >> 4;
        ChunkPos pos = new ChunkPos(chunkX, chunkZ);

        // Получаем координаты руды
        List<BlockPos> ores = SeedCalculator.getOreInChunk(SeedOre.serverSeed, pos);

        if (!ores.isEmpty()) {
            // Тут скоро будет магия отрисовки
        }
    } // Конец метода onRender
} // Конец класса WorldRendererMixin
