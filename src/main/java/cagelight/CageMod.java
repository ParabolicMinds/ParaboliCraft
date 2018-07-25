package cagelight;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = CageMod.MODID, name = CageMod.NAME, version = CageMod.VERSION)
public class CageMod
{
    public static final String MODID = "cagemod";
    public static final String NAME = "Cagelight's Mod";
    public static final String VERSION = "1.0";

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();

        CageModRegistration.registerDimensions();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

    }
}
