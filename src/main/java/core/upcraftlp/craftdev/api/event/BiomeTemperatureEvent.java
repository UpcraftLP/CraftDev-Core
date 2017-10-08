package core.upcraftlp.craftdev.api.event;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * fired on the {@link MinecraftForge#EVENT_BUS} when trying to determine
 * the Temperature of a Biome in {@link Biome#getTemperature(BlockPos)}.
 * @author UpcraftLP
 *
 */
public class BiomeTemperatureEvent extends Event { //! do NOT extend BiomeEvent, as this will error due to Forge's logic of calling all sublcasses of BiomeEvent with only a biome as parameter.

    private float temperature;
    private BlockPos pos;
    private Biome biome;

    public BiomeTemperatureEvent(Biome biome, BlockPos posIn, float temperatureIn) {
        super();
        this.temperature = temperatureIn;
        this.pos = posIn;
        this.biome = biome;
    }

    public Biome getBiome() {return this.biome; }
    
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
