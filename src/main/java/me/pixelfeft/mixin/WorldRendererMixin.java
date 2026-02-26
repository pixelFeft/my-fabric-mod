package me.pixelfeft.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
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
        
        // 1. Проверяем, ввел ли пользователь сид
        if (SeedOre.serverSeed == 0) return;

        // 2. Получаем позицию камеры и текущий чанк
        Vec3d cameraPos = camera.getPos();
        int chunkX = (int) Math.floor(cameraPos.x) >> 4;
        int chunkZ = (int) Math.floor(cameraPos.z) >> 4;
        ChunkPos pos = new ChunkPos(chunkX, chunkZ);

        // 3. Запрашиваем список предсказанных руд у калькулятора
        List<SeedCalculator.OreNode> ores = SeedCalculator.getOreInChunk(SeedOre.serverSeed, pos);

        if (!ores.isEmpty()) {
            // 4. Настройка рендеринга (отключаем глубину, чтобы видеть сквозь блоки)
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.setShader(GameRenderer::getPositionColorProgram);

            Tessellator tessellator = Tessellator.getInstance();
            // В 1.21.10 используем BufferBuilder через tessellator.begin()
            BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);

            for (SeedCalculator.OreNode node : ores) {
                // Вычисляем координаты относительно игрока
                float x = (float) (node.pos.getX() - cameraPos.x);
                float y = (float) (node.pos.getY() - cameraPos.y);
                float z = (float) (node.pos.getZ() - cameraPos.z);

                // Извлекаем цвета из HEX (0xRRGGBB)
                int r = (node.color >> 16) & 0xFF;
                int g = (node.color >> 8) & 0xFF;
                int b = node.color & 0xFF;

                // Рисуем крестик (две диагонали) внутри блока руды
                //
            }
        }
    }
}
