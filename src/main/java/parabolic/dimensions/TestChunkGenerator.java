package parabolic.dimensions;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import parabolic.util.SimplexNoise;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
reference: {@link net.minecraft.world.gen.ChunkGeneratorOverworld}
 */

public class TestChunkGenerator implements IChunkGenerator {

    private final World world;
    private final Random rand;

    private SimplexNoise testNoise;

    public TestChunkGenerator(World worldIn, long seedIn) {
        world = worldIn;
        rand = new Random(seedIn);

        testNoise = new SimplexNoise(rand);
    }

    @Override
    public Chunk generateChunk(int chunk_x, int chunk_z) {
        this.rand.setSeed((long)chunk_x * 341873128712L + (long)chunk_z * 132897987541L);
        ChunkPrimer primer = new ChunkPrimer();

        double[] cdata = testNoise.generate(null, chunk_x * 16, 0, chunk_z * 16, 16, 128, 16, 0.125, 0.125, 0.125);

        for (int x = 0; x < 16; x++) for (int z = 0; z < 16; z++) {
            for (int y = 0; y < 128; y++) {
                double v = cdata[x * 128 * 16 + z * 128 + y];
                if (v > 0.2) primer.setBlockState(x, y , z, Blocks.STONE.getDefaultState());
                else if (v > 0) primer.setBlockState(x, y, z, Blocks.DIRT.getDefaultState());
            }
            primer.setBlockState(x, 0, z, Blocks.BEDROCK.getDefaultState());
        }

        Chunk chunk = new Chunk(this.world, primer, chunk_x, chunk_z);
        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public void populate(int x, int z) {

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