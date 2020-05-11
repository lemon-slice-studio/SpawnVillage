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

    public static String struct = "Village";
    public static String biome = "";
    public static int biomeSearchStepLong = 64;
    public static int biomeSearchRange = 1600;
    private static ConfigValue<String> structCfg;
    private static ForgeConfigSpec.IntValue biomeSearchRangeCfg;
    private static ForgeConfigSpec.IntValue biomeSearchStepLongCfg;
    private static ConfigValue<String> biomeCfg;

    static {
        final Pair<ModMainConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ModMainConfig::new);
        spec = specPair.getRight();
    }

    private ModMainConfig(ForgeConfigSpec.Builder builder) {
        structCfg = builder
                .comment("Struct(namespaced id) player will spawn.","https://minecraft.gamepedia.com/Commands/locate","unless if spawn-biome is define!")
                .define("spawn-struct","Village");

        biomeCfg = builder
                .comment("Biome(namespaced id) player will spawn.","https://minecraft.gamepedia.com/Biome")
                .define("spawn-biome","");

        biomeSearchStepLongCfg = builder
                .defineInRange("biome-search-step-long",biomeSearchStepLong,64,320);

        biomeSearchRangeCfg = builder
                .defineInRange("biome-serch-range",biomeSearchRange,640,32000);
    }

    public static void refresh() {
        struct = structCfg.get();
        biome = biomeCfg.get();
        biomeSearchStepLong = biomeSearchStepLongCfg.get();
        biomeSearchRange = biomeSearchRangeCfg.get();
    }

    @SubscribeEvent
    public static void onFileChange(ModConfig.Reloading event) {
        ((CommentedFileConfig) event.getConfig().getConfigData()).load();
        refresh();
    }
}