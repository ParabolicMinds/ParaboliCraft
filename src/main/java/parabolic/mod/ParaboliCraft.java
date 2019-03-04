package parabolic.mod;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

import parabolic.biomes.*;
import parabolic.dimensions.*;
import parabolic.items.*;

@Mod(modid = ParaboliCraft.MODID, name = ParaboliCraft.NAME, version = ParaboliCraft.VERSION)
@Mod.EventBusSubscriber
public class ParaboliCraft {

    public static final String MODID = "parabolicraft";
    public static final String NAME = "ParaboliCraft";
    public static final String VERSION = "pre-alpha volatile";

    public static Logger logger;

    // ======== INITIALIZE ========

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        registerDimensions();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        //event.getRegistry().register(TNTULTRA_ENT);
    }

    // ======== BIOMES ========

    public static final Biome BIOME_ULTRAJUNGLE = new BiomeUltraJungle("ultra_jungle");
    public static final Biome BIOME_CRYSTALDESERT = new BiomeCrystalDesert("crystal_desert");
    public static final Biome BIOME_SURFACECAVERNS = new BiomeSurfaceCaverns("surface_caverns");

    @SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event) {
        event.getRegistry().registerAll(
                BIOME_ULTRAJUNGLE,
                BIOME_CRYSTALDESERT,
                BIOME_SURFACECAVERNS
        );
    }

    // ======== BLOCKS ========

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        //event.getRegistry().registerAll();
    }

    // ======== DIMENSION ========

    public static final int DIMENSION_ENDURAI_ID = 90904;
    public static final int DIMENSION_XIRK_ID = 90905;
    public static final int DIMENSION_ARCADIA_ID = 90906;

    public static void registerDimensions() {
        DimensionManager.registerDimension(DIMENSION_ENDURAI_ID, EnduraiDimension.DIM_TYPE);
        DimensionManager.registerDimension(DIMENSION_XIRK_ID, XirkDimension.DIM_TYPE);
        DimensionManager.registerDimension(DIMENSION_ARCADIA_ID, ArcadiaDimension.DIM_TYPE);
    }

    // ======== ITEMS ========

    public static Item ITEM_TELEPORTER_ENDURAI = new TeleporterItem(EnduraiDimension.DIM_TYPE, EnduraiDimension.TELEPORTER);
    public static Item ITEM_TELEPORTER_XIRK = new TeleporterItem(XirkDimension.DIM_TYPE, XirkDimension.TELEPORTER);
    public static Item ITEM_TELEPORTER_ARCADIA = new TeleporterItem(ArcadiaDimension.DIM_TYPE, ArcadiaDimension.TELEPORTER);

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                ITEM_TELEPORTER_ENDURAI,
                ITEM_TELEPORTER_XIRK,
                ITEM_TELEPORTER_ARCADIA
        );
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent evt) {
        ModelLoader.setCustomModelResourceLocation(ITEM_TELEPORTER_ENDURAI, 0, new ModelResourceLocation(ITEM_TELEPORTER_ENDURAI.getRegistryName().toString()));
        ModelLoader.setCustomModelResourceLocation(ITEM_TELEPORTER_XIRK, 0, new ModelResourceLocation(ITEM_TELEPORTER_XIRK.getRegistryName().toString()));
        ModelLoader.setCustomModelResourceLocation(ITEM_TELEPORTER_ARCADIA, 0, new ModelResourceLocation(ITEM_TELEPORTER_ARCADIA.getRegistryName().toString()));
    }
}
