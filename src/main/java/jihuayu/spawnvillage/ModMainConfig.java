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
    private static ConfigValue<String> structCfg;

    static {
        final Pair<ModMainConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ModMainConfig::new);
        spec = specPair.getRight();
    }

    private ModMainConfig(ForgeConfigSpec.Builder builder) {
        structCfg = builder
                .comment("Struct player will spawn.\nhttps://minecraft.gamepedia.com/Commands/locate")
                .define("spawn-struct","Village");
    }

    public static void refresh() {
        struct = structCfg.get();
    }
    @SubscribeEvent
    public static void onFileChange(ModConfig.Reloading event) {
        ((CommentedFileConfig) event.getConfig().getConfigData()).load();
        refresh();
    }
}