package core.upcraftlp.craftdev.API.event;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.BiomeEvent;

/**
 * fired on the {@link MinecraftForge#EVENT_BUS} when trying to determine
 * the Temperature of a Biome in {@link Biome#getTemperature()}.
 * @author UpcraftLP
 *
 */
public class BiomeTemperatureEvent extends BiomeEvent {

    private float temperature;
    private BlockPos pos;
    
    public BiomeTemperatureEvent(Biome biome, BlockPos posIn, float temperatureIn) {
        super(biome);
        this.temperature = temperatureIn;
        this.pos = posIn;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public float getTemperature() {
        return this.temperature;
    }
    
    public void setTemperature(int biomeTemperature) {
        this.temperature = biomeTemperature;
    }

}
