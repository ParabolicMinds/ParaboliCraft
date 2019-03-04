package parabolic.dimensions;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import parabolic.mod.ParaboliCraft;

@Mod.EventBusSubscriber
public class ArcadiaDimension extends WorldProvider {

    public static final DimensionType DIM_TYPE = DimensionType.register("arcadia","_arcadia", ParaboliCraft.DIMENSION_ARCADIA_ID, ArcadiaDimension.class, false);
    public static ITeleporter TELEPORTER = new SimpleTeleporter();

    public DimensionType getDimensionType() {
        return DIM_TYPE;
    }

    @Override
    public float getCloudHeight() {
        return 300;
    }

    @Override
    public void init() {
        this.hasSkyLight = true;
        this.biomeProvider = new BiomeProviderSingle(ParaboliCraft.BIOME_SURFACECAVERNS);
        this.world.setSeaLevel(37);
    }

    @Override
    public boolean isSurfaceWorld() {
        return true;
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new ArcadiaChunkGenerator(this.world, this.world.getSeed());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z)
    {
        return false;
    }

    @Override
    public Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {
        return super.getSkyColor(cameraEntity, partialTicks);
    }

    @Override
    public Vec3d getCloudColor(float partialTicks) {
        return new Vec3d(1, 1, 1);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void overrideFogDistance(EntityViewRenderEvent.RenderFogEvent evt) {
        if (evt.getEntity().dimension != ParaboliCraft.DIMENSION_ARCADIA_ID) return;

        double v = Math.min(evt.getFarPlaneDistance(), 192.0F) * 0.5F;

        GlStateManager.setFogStart(evt.getFarPlaneDistance() * 0.05F);;
        GlStateManager.setFogEnd((float)MathHelper.clampedLerp(v * 2, v * 5, ((float)evt.getEntity().posY - 192) / 8));
        ParaboliCraft.logger.info(evt.getEntity().posY);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void overrideFogColor(EntityViewRenderEvent.FogColors evt) {
        if (evt.getEntity().dimension != ParaboliCraft.DIMENSION_ARCADIA_ID) return;

        Vec3d fogColor = evt.getEntity().world.getFogColor((float)evt.getRenderPartialTicks());
        evt.setRed((float)MathHelper.clampedLerp(0, fogColor.x, (evt.getEntity().posY - 192) / 8));
        evt.setGreen((float)MathHelper.clampedLerp(0, fogColor.y, (evt.getEntity().posY - 192) / 8));
        evt.setBlue((float)MathHelper.clampedLerp(0, fogColor.z, (evt.getEntity().posY - 192) / 8));
    }
}
