package cagelight.dimensions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

public class TestTeleporter implements ITeleporter {

    @Override
    public void placeEntity(World world, Entity entity, float yaw) {
        double dist = -1.0;
        int mX = 0, mY = 0, mZ = 0;
        int sX = MathHelper.floor(entity.posX);
        int sZ = MathHelper.floor(entity.posZ);
        BlockPos.MutableBlockPos blockpos = new BlockPos.MutableBlockPos();

        for (int cX = sX - 16; cX <= sX + 16; cX++) {
            for (int cZ = sZ - 16; cZ <= sZ + 16; cZ++) {
                for (int cY = world.getActualHeight() - 1; cY >= 0; cY--) {
                    if (!world.isAirBlock(blockpos.setPos(cX, cY, cZ))) continue;
                    while (cY > 0 && world.isAirBlock(blockpos.setPos(cX, cY - 1, cZ))) cY--;

                    double dX = (double)cX + 0.5D - entity.posX;
                    double dY = (double)cY + 0.5D - entity.posY;
                    double dZ = (double)cZ + 0.5D - entity.posZ;
                    double dist2 = dX * dX + dY * dY + dZ * dZ;
                    if (dist < 0 || dist2 < dist) {
                        dist = dist2;
                        mX = cX;
                        mY = cY;
                        mZ = cZ;
                    }
                }
            }
        }

        if (dist >= 0) {
            if (entity instanceof EntityPlayerMP) {
                ((EntityPlayerMP)entity).connection.setPlayerLocation(mX, mY, mZ, entity.rotationYaw, entity.rotationPitch);
            }
            else {
                entity.setLocationAndAngles(mX, mY, mZ, entity.rotationYaw, entity.rotationPitch);
            }
        }

        // TODO -- couldn't find a place to land, do something about it
    }

    @Override
    public boolean isVanilla() {
        return false;
    }
}
