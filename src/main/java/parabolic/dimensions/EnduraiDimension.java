package parabolic.dimensions;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import parabolic.mod.ParaboliCraft;

@Mod.EventBusSubscriber
public class EnduraiDimension extends WorldProvider {

    public static final DimensionType DIM_TYPE = DimensionType.register("endurai","_endurai", ParaboliCraft.DIMENSION_ENDURAI_ID, EnduraiDimension.class, false);
    public static ITeleporter TELEPORTER = new SimpleTeleporter();

    public DimensionType getDimensionType() {
        return DIM_TYPE;
    }

    @Override
    public float getCloudHeight() {
        return 280;
    }

    @Override
    public void init() {
        this.hasSkyLight = true;
        this.biomeProvider = new BiomeProviderSingle(ParaboliCraft.BIOME_ULTRAJUNGLE);
        this.world.setSeaLevel(37);
    }

    @Override
    public boolean isSurfaceWorld() {
        return true;
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new EnduraiChunkGenerator(this.world, this.world.getSeed());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
        double m = world.getCelestialAngleRadians(p_76562_2_);
        m = Math.cos(m);
        m = (m + 1) / 2;

        double rA = 0, rB = 0;
        double gA = 0.2, gB = 0.4;
        double bA = 0.1, bB = 0.1;

        return new Vec3d(
                MathHelper.clampedLerp(rA, rB, m),
                MathHelper.clampedLerp(gA, gB, m),
                MathHelper.clampedLerp(bA, bB, m)
        );
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z)
    {
        return false;
    }

    @Override
    public Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {

        double m = world.getCelestialAngleRadians(partialTicks);
        m = Math.cos(m);
        m = (m + 1) / 2;

        double rA = 0, rB = 0;
        double gA = 0.2, gB = 0.4;
        double bA = 0.1, bB = 0.1;

        return new Vec3d(
                MathHelper.clampedLerp(rA, rB, m),
                MathHelper.clampedLerp(gA, gB, m),
                MathHelper.clampedLerp(bA, bB, m)
        );
    }

    @Override
    public Vec3d getCloudColor(float partialTicks) {
        return new Vec3d(0.2f, 0.8f, 0.4f);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void overrideFog(EntityViewRenderEvent.RenderFogEvent evt) {
        if (evt.getEntity().dimension != ParaboliCraft.DIMENSION_ENDURAI_ID) return;

        double mult = evt.getEntity().world.getCelestialAngleRadians((float)evt.getRenderPartialTicks());
        mult = Math.cos(mult);
        mult = (mult + 1) / 2;
        double v = Math.min(evt.getFarPlaneDistance(), 192.0F) * 0.5F;

        GlStateManager.setFogStart(evt.getFarPlaneDistance() * 0.05F);;
        GlStateManager.setFogEnd((float)MathHelper.clampedLerp(v * 4, v * 2, mult));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void overrideFogColor(EntityViewRenderEvent.FogColors evt) {
        if (evt.getEntity().dimension != ParaboliCraft.DIMENSION_ENDURAI_ID) return;

        Vec3d fogColor = evt.getEntity().world.getFogColor((float)evt.getRenderPartialTicks());
        evt.setRed((float)fogColor.x);
        evt.setGreen((float)fogColor.y);
        evt.setBlue((float)fogColor.z);
    }
}
