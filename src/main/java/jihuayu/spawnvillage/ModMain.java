package jihuayu.spawnvillage;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import static jihuayu.spawnvillage.ModMain.MOD_ID;

@Mod(MOD_ID)
public class ModMain {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "spawnvillage";
    public ModMain(){
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModMainConfig.spec, MOD_ID+".toml");
    }
}
