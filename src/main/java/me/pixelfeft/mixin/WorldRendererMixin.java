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
    private void onRender(RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, 
                          GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, 
                          Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {
        
        // 1. Проверяем, ввел ли ты сид через /setseed
        if (me.pixelfeft.SeedOre.serverSeed == 0) return;

        // 2. Получаем позицию камеры (твои глаза в игре)
        net.minecraft.util.math.Vec3d cameraPos = camera.getPos();

        // 3. Берем текущий чанк
        int chunkX = (int) cameraPos.x >> 4;
        int chunkZ = (int) cameraPos.z >> 4;
        net.minecraft.util.math.ChunkPos pos = new net.minecraft.util.math.ChunkPos(chunkX, chunkZ);

        // 4. Считаем руду через наш SeedCalculator
        java.util.List<net.minecraft.util.math.BlockPos> ores = me.pixelfeft.SeedCalculator.getOreInChunk(me.pixelfeft.SeedOre.serverSeed, pos);

        // 5. Пока что просто выводим в чат/консоль для теста (чтобы не лагало на FX-4130)
        if (!ores.isEmpty()) {
            // Здесь скоро будет код для отрисовки линий (Tessellator)
        }
    }
