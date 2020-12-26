package jihuayu.spawnvillage;

import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD)
public final class ModMainConfig {
    static final ForgeConfigSpec spec;
    public static String struct = "village";
    public static String biome = "";
    public static int biomeSearchRange = 6400;
    public static int structSearchRange = 3200;
    private static ConfigValue<String> structCfg;
    private static ForgeConfigSpec.IntValue biomeSearchRangeCfg;
    private static ForgeConfigSpec.IntValue structSearchRangeCfg;
    private static ForgeConfigSpec.IntValue biomeSearchStepLongCfg;
    private static ConfigValue<String> biomeCfg;

    private ModMainConfig(ForgeConfigSpec.Builder builder) {
        structCfg = builder.comment(new String[]{"Struct(namespaced id) player will spawn.", "https://minecraft.gamepedia.com/Commands/locate", "Useless if spawn-biome is defined!"}).define("spawn-struct", "Village");
        biomeCfg = builder.comment(new String[]{"Biome(namespaced id) player will spawn.", "https://minecraft.gamepedia.com/Biome"}).define("spawn-biome", "");
        biomeSearchRangeCfg = builder.defineInRange("biome-search-range", biomeSearchRange, 640, 32000);
        structSearchRangeCfg = builder.defineInRange("struct-search-range", structSearchRange, 100, 32000);
    }

    public static void refresh() {
        struct = ((String)structCfg.get()).toLowerCase();
        biome = ((String)biomeCfg.get()).toLowerCase();
        biomeSearchRange = (Integer)biomeSearchRangeCfg.get();
        structSearchRange = (Integer)structSearchRangeCfg.get();
    }

    @SubscribeEvent
    public static void onFileChange(ModConfig.Reloading event) {
        ((CommentedFileConfig)event.getConfig().getConfigData()).load();
        refresh();
    }

    static {
        Pair<ModMainConfig, ForgeConfigSpec> specPair = (new ForgeConfigSpec.Builder()).configure(ModMainConfig::new);
        spec = (ForgeConfigSpec)specPair.getRight();
    }
}