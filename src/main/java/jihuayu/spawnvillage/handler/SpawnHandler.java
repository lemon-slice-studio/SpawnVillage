package jihuayu.spawnvillage.handler;

import jihuayu.spawnvillage.ModMain;
import jihuayu.spawnvillage.ModMainConfig;
import mezz.jei.events.PlayerJoinedWorldEvent;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber
public class SpawnHandler {
    @SubscribeEvent
    public static void onCreateSpawnPosition(WorldEvent.CreateSpawnPosition event) {
        ModMainConfig.refresh();
        IWorld world = event.getWorld();
        if (world instanceof ServerWorld) {
            if (ModMainConfig.biome.isEmpty()) {
                BlockPos pos = ((ServerWorld) world).findNearestStructure(ModMainConfig.struct, new BlockPos(0, 60, 0), 1000, false);
                if (pos != null) {
                    for (int i = 40; i < 100; i++) {
                        BlockState block = world.getBlockState(pos.add(0, i, 0));
                        if (block.isAir()) {
                            ((ServerWorld) world).setSpawnPoint(pos.add(0, i, 0));
                            event.setCanceled(true);
                            return;
                        }
                    }
                } else {
                    ModMain.LOGGER.error(String.format("Could found %s in range 1000m", ModMainConfig.struct));
                }
            } else {
                BlockPos pos = findBiome((ServerWorld) world, ModMainConfig.biome, new BlockPos(0, 0, 0), 0);
                if (pos != null) {
                    for (int i = 40; i < 100; i++) {
                        BlockState block = world.getBlockState(pos.add(0, i, 0));
                        if (block.isAir()) {
                            ((ServerWorld) world).setSpawnPoint(pos.add(0, i, 0));
                            event.setCanceled(true);
                            return;
                        }
                    }
                } else {
                    ModMain.LOGGER.error(String.format("Could found %s in range %dm", ModMainConfig.biome, ModMainConfig.biomeSearchRange));
                }
            }
        }
    }

    private static BlockPos findBiome(ServerWorld world, String name, BlockPos pos, int step) {
        BlockPos ans;
        if (step > ModMainConfig.biomeSearchRange / ModMainConfig.biomeSearchStepLong) return null;
        if (Objects.equals(world.getBiome(pos).getRegistryName(), (new ResourceLocation(name)))) {
            return pos;
        } else {
            ans = findBiome(world, name, pos.add(0, 0, ModMainConfig.biomeSearchStepLong), step + 1);
            if (ans != null) return ans;
            ans = findBiome(world, name, pos.add(ModMainConfig.biomeSearchStepLong, 0, 0), step + 1);
            return ans;
        }
    }
}