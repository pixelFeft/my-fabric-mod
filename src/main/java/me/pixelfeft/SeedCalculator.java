package me.pixelfeft;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.RandomSeed;
import java.util.ArrayList;
import java.util.List;

public class SeedCalculator {
    
    // Специальный контейнер для руды
    public static class OreNode {
        public BlockPos pos;
        public int color; // RGB цвет

        public OreNode(BlockPos pos, int color) {
            this.pos = pos;
            this.color = color;
        }
    }

    public static List<OreNode> getOreInChunk(long seed, ChunkPos pos) {
        List<OreNode> ores = new ArrayList<>();
        ChunkRandom random = new ChunkRandom(new CheckedRandom(RandomSeed.getSeed()));

        // 1. АЛМАЗЫ (Голубой: 0x00FFFF)
        random.setCarverSeed(seed, pos.x, pos.z);
        for (int i = 0; i < 4; i++) {
            BlockPos p = new BlockPos(pos.getStartX() + random.nextInt(16), random.nextInt(48) - 64, pos.getStartZ() + random.nextInt(16));
            ores.add(new OreNode(p, 0x00FFFF));
        }

        // 2. НЕЗЕРИТ (Темно-красный: 0x800000)
        random.setRegionSeed(seed, pos.x, pos.z, 12345);
        for (int i = 0; i < 2; i++) {
            BlockPos p = new BlockPos(pos.getStartX() + random.nextInt(16), random.nextInt(100) + 10, pos.getStartZ() + random.nextInt(16));
            ores.add(new OreNode(p, 0x800000));
        }

        // 3. ИЗУМРУДЫ (Ярко-зеленый: 0x00FF00)
        for (int i = 0; i < 3; i++) {
            BlockPos p = new BlockPos(pos.getStartX() + random.nextInt(16), random.nextInt(200) + 60, pos.getStartZ() + random.nextInt(16));
            ores.add(new OreNode(p, 0x00FF00));
        }

        return ores;
    }
}
