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
        
        if (SeedOre.serverSeed == 0) return;

        Vec3d cameraPos = camera.getPos();
        ChunkPos chunkPos = new ChunkPos((int) Math.floor(cameraPos.x) >> 4, (int) Math.floor(cameraPos.z) >> 4);

        List<SeedCalculator.OreNode> ores = SeedCalculator.getOreInChunk(SeedOre.serverSeed, chunkPos);

        if (!ores.isEmpty()) {
            // Настройка рендера
            RenderSystem.setShader(GameRenderer::getPositionColorProgram);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableCull();
            // Вместо depthMask используем прямое переключение через RenderSystem, если доступно
            // или оставляем так для совместимости
            
            Tessellator tessellator = Tessellator.getInstance();
            // В некоторых маппингах 1.21 VertexFormat.DrawMode лежит внутри VertexFormat
            BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);

            for (SeedCalculator.OreNode node : ores) {
                float x = (float) (node.pos.getX() - cameraPos.x);
                float y = (float) (node.pos.getY() - cameraPos.y);
                float z = (float) (node.pos.getZ() - cameraPos.z);

                int r = (node.color >> 16) & 0xFF;
                int g = (node.color >> 8) & 0xFF;
                int b = node.color & 0xFF;

                // Рисуем одну линию
                bufferBuilder.vertex(x, y, z).color(r, g, b, 255);
                bufferBuilder.vertex(x + 1, y + 1, z + 1).color(r, g, b, 255);
            }
            
            // Пробуем вызвать отрисовку через встроенный метод буфера
            BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
            
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
        }
    }
}
