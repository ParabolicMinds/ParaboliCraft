package parabolic.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

public class TeleporterItem extends Item {

    private final DimensionType dim_type;
    private final ITeleporter teleporter;

    public TeleporterItem(DimensionType dim_type, ITeleporter teleporter) {
        this.dim_type = dim_type;
        this.teleporter = teleporter;

        String name = "teleporter_" + dim_type.getName();
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (worldIn.isRemote) return super.onItemRightClick(worldIn, playerIn, handIn);
        if (playerIn.dimension == dim_type.getId())
            playerIn.changeDimension(0, this.teleporter);
        else
            playerIn.changeDimension(dim_type.getId(), this.teleporter);
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
}
