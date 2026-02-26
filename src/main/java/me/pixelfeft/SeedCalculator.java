package me.pixelfeft;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.random.ChunkRandom;
import net.minecraft.world.gen.random.RandomSeed;
import net.minecraft.world.gen.random.Xoroshiro128PlusPlusRandomImpl;

import java.util.ArrayList;
import java.util.List;

public class SeedCalculator {
    
    public static List<BlockPos> getOreInChunk(long seed, ChunkPos pos) {
        List<BlockPos> locations = new ArrayList<>();
        
        // В 1.21.10 используется алгоритм Xoroshiro для рандома
        ChunkRandom random = new ChunkRandom(new Xoroshiro128PlusPlusRandomImpl(RandomSeed.getSeed()));
        
        // Устанавливаем сид для конкретного чанка (логика алмазов)
        random.setCarverSeed(seed, pos.x, pos.z);
        
        // Пример упрощенной логики: в каждом чанке обычно 1-8 попыток генерации алмазов
        for (int i = 0; i < 8; i++) {
            int x = pos.getStartX() + random.nextInt(16);
            int y = random.nextInt(64) - 64; // Глубина от -64 до 0
            int z = pos.getStartZ() + random.nextInt(16);
            
            locations.add(new BlockPos(x, y, z));
        }
        
        return locations;
    }
}
