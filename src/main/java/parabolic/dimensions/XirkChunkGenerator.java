package parabolic.dimensions;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import parabolic.util.INoise;
import parabolic.util.SimplexOctaves;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
reference: {@link net.minecraft.world.gen.ChunkGeneratorOverworld}
 */

public class XirkChunkGenerator implements IChunkGenerator {

    private final World world;
    private final Random rand;

    private INoise testNoise;

    public XirkChunkGenerator(World worldIn, long seedIn) {
        world = worldIn;
        rand = new Random(seedIn);

        testNoise = new SimplexOctaves(rand, 6,1.0 / 256);
    }

    @Override
    public Chunk generateChunk(int chunk_x, int chunk_z) {
        this.rand.setSeed((long)chunk_x * 341873128712L + (long)chunk_z * 132897987541L);
        ChunkPrimer primer = new ChunkPrimer();

        final int yMax = 254;

        double[] cdata = testNoise.generate(null, 16, yMax, 16, chunk_x * 16, 0, chunk_z * 16);

        for (int x = 0; x < 16; x++) for (int z = 0; z < 16; z++) {
            for (int y = 0; y < yMax; y++) {

                double v = cdata[x * yMax * 16 + z * yMax + y];
                double m = ((yMax / 2.0) - y) / (yMax / 2.0);
                v += Math.abs(Math.pow(m, 3));

                if (v > 0.5) primer.setBlockState(x, y+1, z, Blocks.STONE.getDefaultState());
                if (v > 0) primer.setBlockState(x, y+1, z, Blocks.SANDSTONE.getDefaultState());
            }
            primer.setBlockState(x, 255, z, Blocks.BEDROCK.getDefaultState());
            primer.setBlockState(x, 0, z, Blocks.BEDROCK.getDefaultState());
        }

        Biome[] biomes = this.world.getBiomeProvider().getBiomes(null, chunk_x * 16, chunk_z * 16, 16, 16);

        if (net.minecraftforge.event.ForgeEventFactory.onReplaceBiomeBlocks(this, chunk_x, chunk_z, primer, this.world)) {

            for (int x = 0; x < 16; x++)
                for (int z = 0; z < 16; z++) {
                    Biome biome = biomes[x + z * 16];
                    biome.genTerrainBlocks(this.world, this.rand, primer, chunk_x * 16 + x, chunk_z * 16 + z, 0);
                }

        }

        Chunk chunk = new Chunk(this.world, primer, chunk_x, chunk_z);
        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public void populate(int chunk_x, int chunk_z) {
        int x = chunk_x * 16, z = chunk_z * 16;
        BlockPos blockpos = new BlockPos(x, 0, z);
        Biome biome = this.world.getBiome(blockpos.add(16, 0, 16));
        this.rand.setSeed(this.world.getSeed());
        long k = this.rand.nextLong() / 2L * 2L + 1L;
        long l = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long)x * k + (long)z * l ^ this.world.getSeed());
        boolean flag = false;

        net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, x, z, flag);

        biome.decorate(this.world, this.rand, new BlockPos(x, 0, z));

        net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, x, z, flag);
    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        return null;
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
        return null;
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z) {

    }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        return false;
    }
}
