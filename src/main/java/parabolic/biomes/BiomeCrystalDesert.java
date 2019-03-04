package parabolic.biomes;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDesert;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public class BiomeCrystalDesert extends BiomeDesert {

    public BiomeCrystalDesert(String nameIn) {
        super(new BiomeProperties(nameIn).setTemperature(1.0F).setRainfall(0.0F).setWaterColor(16732160));
        this.setRegistryName(nameIn);
        this.decorator.grassPerChunk = 0;
        this.decorator.flowersPerChunk = 0;
        this.decorator.sandPatchesPerChunk = 0;
        this.decorator.gravelPatchesPerChunk = 0;
        this.decorator.reedsPerChunk = 0;
        this.decorator.generateFalls = false;
        this.decorator.mushroomsPerChunk = 0;
        this.decorator.waterlilyPerChunk = 0;

        this.topBlock = Blocks.SAND.getDefaultState();
        this.fillerBlock = Blocks.SAND.getDefaultState();
    }

    @Override
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x_global, int z_global, double noiseVal) {
        int seaLevel = worldIn.getSeaLevel();
        int depth = -1;

        int x = x_global & 15;
        int z = z_global & 15;

        for (int y = 255; y >= 0; --y) {

            if (y <= rand.nextInt(5)) {
                chunkPrimerIn.setBlockState(x, y, z, BEDROCK);
                continue;
            }

            if (y >= 255 - rand.nextInt(5)) {
                chunkPrimerIn.setBlockState(x, y, z, BEDROCK);
                continue;
            }

            IBlockState blockState = chunkPrimerIn.getBlockState(x, y, z);
            Material mat = blockState.getMaterial();
            Block block = blockState.getBlock();

            if (mat == Material.AIR) {
                if (depth > 0) depth = 0;
                continue;
            }

            if (block == Blocks.SANDSTONE) {
                if (depth < 1) {
                    chunkPrimerIn.setBlockState(x, y, z, Blocks.SAND.getDefaultState());
                }
                depth++;
            }
        }
    }
}
