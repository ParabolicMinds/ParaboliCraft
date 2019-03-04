package parabolic.biomes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

import static net.minecraft.block.BlockFlower.EnumFlowerType.BLUE_ORCHID;

public class BiomeUltraJungle extends BiomeJungle {

    public BiomeUltraJungle(String nameIn) {
        super(false, new BiomeProperties(nameIn).setTemperature(0.95F).setRainfall(1.0F).setWaterColor(5294200));
        this.setRegistryName(nameIn);
        this.decorator.grassPerChunk = 32;
        this.decorator.flowersPerChunk = 8;
        this.decorator.sandPatchesPerChunk = 0;
        this.decorator.gravelPatchesPerChunk = 0;
        this.decorator.reedsPerChunk = 64;
        this.decorator.generateFalls = false;
        this.decorator.mushroomsPerChunk = 4;
        this.decorator.waterlilyPerChunk = 16;

        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityOcelot.class, 2, 1, 1));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityParrot.class, 40, 5, 8));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityChicken.class, 10, 1, 4));
    }

    @Override
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x_global, int z_global, double noiseVal) {
        int seaLevel = worldIn.getSeaLevel();
        IBlockState topBlock = this.topBlock;
        IBlockState fillerBlock = this.fillerBlock;
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
                if (y <= seaLevel) {
                    chunkPrimerIn.setBlockState(x, y, z, WATER);
                }
                continue;
            }

            if (block == Blocks.STONE) {
                if (y < seaLevel) {
                    if (depth < 2) chunkPrimerIn.setBlockState(x, y, z, GRAVEL);
                } else {
                    if (depth == -1) {
                        chunkPrimerIn.setBlockState(x, y, z, topBlock);
                    } else if (depth < 2) {
                        chunkPrimerIn.setBlockState(x, y, z, fillerBlock);
                    }
                }
                depth++;
            }
        }
    }

    /*
    @SideOnly(Side.CLIENT)
    public int getGrassColorAtPos(BlockPos pos)
    {
        return 5294200;
    }

    @SideOnly(Side.CLIENT)
    public int getFoliageColorAtPos(BlockPos pos)
    {
        return 5294200;
    }
    */

    @Override
    public WorldGenerator getRandomWorldGenForGrass(Random rand) {
        return rand.nextInt(2) == 0 ? new WorldGenTallGrass(BlockTallGrass.EnumType.FERN) : new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
    }

    @Override
    public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos) {
        return BLUE_ORCHID;
    }
}
