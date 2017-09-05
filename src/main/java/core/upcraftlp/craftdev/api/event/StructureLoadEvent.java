package core.upcraftlp.craftdev.api.event;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

/**
 * @author UpcraftLP
 * fired on the {@link net.minecraftforge.common.MinecraftForge#TERRAIN_GEN_BUS}
 */
@Cancelable
public class StructureLoadEvent extends WorldEvent {

    private BlockPos position;
    private String structureName;
    private PlacementSettings placementSettings;
    private Template template;
    private boolean isLoadingFromAssets;

    public StructureLoadEvent(final World world, BlockPos pos, final String structureName, PlacementSettings settings, Template template) {
        super(world);
        this.structureName = structureName;
        this.placementSettings = settings;
        this.template = template;
        this.position = pos;
        this.isLoadingFromAssets = structureName.contains(":");
    }

    @Override
    public boolean isCancelable() {
        return true;
    }

    public PlacementSettings getPlacementSettings() {
        return this.placementSettings;
    }

    public Template getTemplate() {
        return this.template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public String getStructureName() {
        return this.structureName;
    }

    public BlockPos getPosition() {
        return this.position;
    }

    public void setPosition(BlockPos pos) {
        this.position = pos;
    }

    public boolean isLoadingFromAssets() {
        return this.isLoadingFromAssets;
    }
}
