package jihuayu.spawnvillage.handler;

import jihuayu.spawnvillage.ModMain;
import jihuayu.spawnvillage.ModMainConfig;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber
public class SpawnHandler {
    @SubscribeEvent
    public static void onCreateSpawnPosition(WorldEvent.CreateSpawnPosition event) {
        ModMainConfig.refresh();
        IWorld world = event.getWorld();
        if (world instanceof ServerWorld) {
            if (ModMainConfig.biome.isEmpty()) {
                Structure<?> temp = ForgeRegistries.STRUCTURE_FEATURES.getValue(new ResourceLocation(ModMainConfig.struct));
                if (temp == null) {
                    ModMain.LOGGER.error(String.format("Error struct name %s", ModMainConfig.struct));
                    return;
                }
                BlockPos pos = ((ServerWorld) world).func_241117_a_(temp, new BlockPos(0, 60, 0), ModMainConfig.structSearchRange, false);
                if (pos != null) {
                    for (int i = 40; i < 100; i++) {
                        BlockState block = world.getBlockState(pos.add(0, i, 0));
                        if (block.isAir()) {
                            ((ServerWorld) world).func_241124_a__(pos.add(0, i, 0),0);
                            event.setCanceled(true);
                            return;
                        }
                    }
                } else {
                    ModMain.LOGGER.error(String.format("Could found %s in range %dm", ModMainConfig.struct, ModMainConfig.structSearchRange));
                }
            } else {
                BlockPos pos = findBiome((ServerWorld) world, ModMainConfig.biome, new BlockPos(0, 0, 0), 0);
                if (pos != null) {
                    for (int i = 40; i < 100; i++) {
                        BlockState block = world.getBlockState(pos.add(0, i, 0));
                        if (block.isAir()) {
                            ((ServerWorld) world).func_241124_a__(pos.add(0, i, 0),0);
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
        Biome temp = ForgeRegistries.BIOMES.getValue(new ResourceLocation(ModMainConfig.biome));
        if (temp == null) {
            ModMain.LOGGER.error(String.format("Error biome name %s", ModMainConfig.biome));
            return null;
        } else {
            return world.func_241116_a_(temp, pos, ModMainConfig.biomeSearchRange, 8);
        }
    }
}