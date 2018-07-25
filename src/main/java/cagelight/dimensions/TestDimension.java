package cagelight.dimensions;

import cagelight.CageModRegistration;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.ChunkGeneratorEnd;
import net.minecraft.world.gen.ChunkGeneratorFlat;
import net.minecraft.world.gen.ChunkGeneratorOverworld;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TestDimension extends WorldProvider {

    public static final DimensionType DIM_TYPE = DimensionType.register("test","_test", CageModRegistration.DIMENSION_TEST_ID, TestDimension.class, false);
    public static ITeleporter TELEPORTER = new TestTeleporter();

    public DimensionType getDimensionType() {
        return DIM_TYPE;
    }

    @Override
    public void init() {
        this.biomeProvider = new BiomeProviderSingle(Biomes.MUTATED_JUNGLE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
        return new Vec3d(0.0, 0.2, 0.1);
    }

    @Override
    public boolean isSurfaceWorld()
    {
        return false;
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new ChunkGeneratorFlat(this.world, this.world.getSeed(), false, null);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z)
    {
        return true;
    }
}
