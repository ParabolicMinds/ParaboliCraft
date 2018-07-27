package parabolic.items;

import parabolic.mod.ParaboliCraftRegistrar;
import parabolic.dimensions.TestDimension;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class TeleporterTest extends Item {

    public static final String name = "teleporter_test";

    public TeleporterTest() {
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (worldIn.isRemote) return super.onItemRightClick(worldIn, playerIn, handIn);
        if (playerIn.dimension == ParaboliCraftRegistrar.DIMENSION_TEST_ID)
            playerIn.changeDimension(0, TestDimension.TELEPORTER);
        else
            playerIn.changeDimension(ParaboliCraftRegistrar.DIMENSION_TEST_ID, TestDimension.TELEPORTER);
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
}
