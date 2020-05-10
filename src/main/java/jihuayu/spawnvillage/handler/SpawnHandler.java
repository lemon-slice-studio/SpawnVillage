package jihuayu.spawnvillage.handler;

import jihuayu.spawnvillage.ModMainConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class SpawnHandler {
    @SubscribeEvent
    public static void onCreateSpawnPosition(WorldEvent.CreateSpawnPosition event) {
        ModMainConfig.refresh();

        IWorld world = event.getWorld();
        if (world instanceof ServerWorld) {
            BlockPos pos = ((ServerWorld) world).findNearestStructure(ModMainConfig.struct, new BlockPos(0, 60, 0), 1000, false);
            if (pos != null) {
                ((ServerWorld) world).setSpawnPoint(pos);
                event.setCanceled(true);
            }
        }
    }
}
