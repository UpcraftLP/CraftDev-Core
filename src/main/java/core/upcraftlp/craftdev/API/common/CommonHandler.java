package core.upcraftlp.craftdev.API.common;

import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class CommonHandler {

    public static void spawnParticles(EnumParticleTypes particleType, World world, boolean forceSpawn, BlockPos pos, int count, double radius) {
        spawnParticles(particleType, world, forceSpawn, pos.getX(), pos.getY(), pos.getZ(), count, radius);
    }

    public static void spawnParticles(EnumParticleTypes particleType, World world, boolean forceSpawn, double x, double y, double z, int count, double radius) {
        if ( world instanceof WorldServer ) ((WorldServer) world).spawnParticle(particleType, forceSpawn, x, y, z, count, radius, radius, radius, 0.005D);
    }

}
