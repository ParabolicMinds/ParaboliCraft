package parabolic.biomes;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public class BiomeSurfaceCaverns extends Biome {
    public BiomeSurfaceCaverns(String nameIn) {
        super(new Biome.BiomeProperties(nameIn).setTemperature(0.5F).setRainfall(0.5F));
        this.setRegistryName(nameIn);
        this.decorator.grassPerChunk = 4;
        this.decorator.flowersPerChunk = 0;
        this.decorator.sandPatchesPerChunk = 0;
        this.decorator.gravelPatchesPerChunk = 0;
        this.decorator.reedsPerChunk = 0;
        this.decorator.generateFalls = false;
        this.decorator.mushroomsPerChunk = 0;
        this.decorator.waterlilyPerChunk = 0;
        this.decorator.treesPerChunk = 8;
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

            IBlockState blockState = chunkPrimerIn.getBlockState(x, y, z);
            Material mat = blockState.getMaterial();
            Block block = blockState.getBlock();

            if (mat == Material.AIR) {
                if (depth > 0) depth = 0;
                continue;
            }

            if (block == Blocks.STONE && y >= 192) {
                if (depth == -1) {
                    chunkPrimerIn.setBlockState(x, y, z, Blocks.GRASS.getDefaultState());
                } else if (depth < 2) {
                    chunkPrimerIn.setBlockState(x, y, z, Blocks.DIRT.getDefaultState());
                }
                depth++;
            }
        }
    }
}
