package parabolic.dimensions;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
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
public class XirkDimension extends WorldProvider {

    public static final DimensionType DIM_TYPE = DimensionType.register("xirk","_xirk", ParaboliCraft.DIMENSION_XIRK_ID, XirkDimension.class, false);
    public static ITeleporter TELEPORTER = new SimpleTeleporter();

    private static Vec3d baseColor = new Vec3d(1.0D, 0.75D, 0.0D);

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
        this.biomeProvider = new BiomeProviderSingle(ParaboliCraft.BIOME_CRYSTALDESERT);
        this.world.setSeaLevel(8);
    }

    @Override
    public boolean isSurfaceWorld() {
        return false;
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new XirkChunkGenerator(this.world, this.world.getSeed());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
        return baseColor.scale(0.5);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z)
    {
        return true;
    }

    @Override
    public Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {
        return baseColor.scale(0.5);
    }

    @Override
    public Vec3d getCloudColor(float partialTicks) {
        return baseColor.scale(0.5);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void overrideFog(EntityViewRenderEvent.RenderFogEvent evt) {
        if (evt.getEntity().dimension != ParaboliCraft.DIMENSION_XIRK_ID) return;

        float v = Math.min(evt.getFarPlaneDistance(), 192.0F) * 0.5F;
        GlStateManager.setFogStart(evt.getFarPlaneDistance() * 0.05F);;
        GlStateManager.setFogEnd(v * 3);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void overrideFogColor(EntityViewRenderEvent.FogColors evt) {
        if (evt.getEntity().dimension != ParaboliCraft.DIMENSION_XIRK_ID) return;

        Vec3d fogColor = evt.getEntity().world.getFogColor((float)evt.getRenderPartialTicks());
        evt.setRed((float)fogColor.x);
        evt.setGreen((float)fogColor.y);
        evt.setBlue((float)fogColor.z);
    }
}
