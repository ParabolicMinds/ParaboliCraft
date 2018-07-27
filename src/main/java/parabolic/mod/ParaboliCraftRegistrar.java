package parabolic.mod;

import parabolic.dimensions.TestDimension;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;

import parabolic.items.*;

@Mod.EventBusSubscriber(modid = ParaboliCraft.MODID)
public class ParaboliCraftRegistrar {

    // ITEMS

    public static Item item_teleporter_test = new TeleporterTest();

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        //event.getRegistry().registerAll();
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        //event.getRegistry().registerAll(new ItemBlock(ultratnt).setRegistryName(ultratnt.getRegistryName()));
        event.getRegistry().registerAll(item_teleporter_test);
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        //event.getRegistry().register(TNTULTRA_ENT);
    }

    // DIMENSIONS

    public static final int DIMENSION_TEST_ID = 90904;

    public static void registerDimensions() {
        DimensionManager.registerDimension(DIMENSION_TEST_ID, TestDimension.DIM_TYPE);
    }
}
