package me.pixelfeft;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.RandomSeed;
import net.minecraft.util.math.random.CheckedRandom;

import java.util.ArrayList;
import java.util.List;

public class SeedCalculator {
    
    public static List<BlockPos> getOreInChunk(long seed, ChunkPos pos) {
        List<BlockPos> locations = new ArrayList<>();
        
        // В 1.21.10 рандом создается через CheckedRandom или LocalRandom
        ChunkRandom random = new ChunkRandom(new CheckedRandom(RandomSeed.getSeed()));
        
        // Устанавливаем сид для конкретного чанка
        random.setCarverSeed(seed, pos.x, pos.z);
        
        // Алгоритм генерации алмазов (упрощенный пример)
        for (int i = 0; i < 8; i++) {
            int x = pos.getStartX() + random.nextInt(16);
            int y = random.nextInt(64) - 64; 
            int z = pos.getStartZ() + random.nextInt(16);
            
            locations.add(new BlockPos(x, y, z));
        }
        
        return locations;
    }
}
