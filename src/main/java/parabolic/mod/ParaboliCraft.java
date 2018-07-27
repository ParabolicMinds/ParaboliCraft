package parabolic.mod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = ParaboliCraft.MODID, name = ParaboliCraft.NAME, version = ParaboliCraft.VERSION)
public class ParaboliCraft
{
    public static final String MODID = "parabolicraft";
    public static final String NAME = "ParaboliCraft";
    public static final String VERSION = "pre-alpha volatile";

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();

        ParaboliCraftRegistrar.registerDimensions();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

    }
}
