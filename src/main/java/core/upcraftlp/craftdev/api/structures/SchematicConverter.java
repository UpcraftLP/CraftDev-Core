package core.upcraftlp.craftdev.api.structures;

import java.util.IllegalFormatFlagsException;
import java.util.Iterator;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.common.util.Constants.NBT;

public class SchematicConverter {
    
  //Schematic file format
    private static final String SCHEMATIC_WIDTH = "Width";
    private static final String SCHEMATIC_HEIGHT = "Height";
    private static final String SCHEMATIC_LENGTH = "Length";
    private static final String SCHEMATIC_BLOCKS = "Blocks";
    private static final String SCHEMATIC_DATA = "Data";
    private static final String SCHEMATIC_TILE_ENTITIES = "TileEntities";
    private static final String SCHEMATIC_ENTITIES = "Entities";
    private static final String SCHEMATIC_KEY_MATERIALS = "Materials";
    private static final String SCHEMATIC_ALPHA_FORMAT = "Alpha";
    
    //NBT file format
    private static final String KEY_POS_X = "x";
    private static final String KEY_POS_Y = "y";
    private static final String KEY_POS_Z = "z";
    private static final String KEY_POS = "pos";
    private static final String KEY_BLOCKSTATE = "state";
    private static final String KEY_TAG_COMPOUND = "nbt";
    private static final String KEY_PALETTE = "palette";
    private static final String KEY_BLOCKS = "blocks";
    private static final String KEY_ENTITIES = "entities";
    private static final String KEY_SIZE = "size";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_DATA_VERSION = "DataVersion";
    private static final int DATA_VERSION = 819; //change this when vanilla changes
    
    /**
     * convert an {@link NBTTagCompound} from the <a href= "http://minecraft.gamepedia.com/Schematic_file_format">schematic file format</a>
     *  to <a href ="http://minecraft.gamepedia.com/Generated_structures_data_file_format">vanilla's NBT file format</a>
     * @return the modifed tag
     */
    @SuppressWarnings("deprecation")
    public static NBTTagCompound convertToNBT(NBTTagCompound schematicTag) {
        String format = schematicTag.getString(SCHEMATIC_KEY_MATERIALS);
        if(!format.equals(SCHEMATIC_ALPHA_FORMAT)) throw new IllegalFormatFlagsException("schematic file is not in \"Alpha\" format!");
        short width = schematicTag.getShort(SCHEMATIC_WIDTH);
        short height = schematicTag.getShort(SCHEMATIC_HEIGHT);
        short length = schematicTag.getShort(SCHEMATIC_LENGTH);
        byte[] blocks = schematicTag.getByteArray(SCHEMATIC_BLOCKS);
        byte[] data = schematicTag.getByteArray(SCHEMATIC_DATA);
        NBTTagList entities = schematicTag.getTagList(SCHEMATIC_ENTITIES, NBT.TAG_LIST);
        NBTTagList tileEntities = schematicTag.getTagList(SCHEMATIC_TILE_ENTITIES, NBT.TAG_LIST);

        //convert everything to a vanilla template
        NBTTagCompound templateCompound = new NBTTagCompound();
        NBTTagList blockTagList = new NBTTagList();
        BasicPalette basicPalette = new BasicPalette();
        
        
        NBTTagCompound[][][] tileEntityTagArray = new NBTTagCompound[height][length][width];
        for(int i = 0; i < tileEntities.tagCount(); i++) {
            NBTTagCompound te = tileEntities.getCompoundTagAt(i);
            tileEntityTagArray[te.getInteger(KEY_POS_Y)][te.getInteger(KEY_POS_Z)][te.getInteger(KEY_POS_X)] = te;
        }
        for(int y = 0; y < height; y++) {
            for(int z = 0; z < length; z++) {
                for(int x = 0; x < width; x++) {
                    
                    NBTTagCompound blockNBT = new NBTTagCompound();
                    NBTTagList posList = new NBTTagList();
                    posList.appendTag(new NBTTagInt(x));
                    posList.appendTag(new NBTTagInt(y));
                    posList.appendTag(new NBTTagInt(z));
                    blockNBT.setTag(KEY_POS, posList);
                    
                    int index = (y*length + z)*width + x;
                    Block block = Block.getBlockById(blocks[index]);
                    
                    blockNBT.setInteger(KEY_BLOCKSTATE, basicPalette.idFor(block.getStateFromMeta(data[index])));
                    NBTTagCompound te = tileEntityTagArray[y][z][x];
                    if(te != null) blockNBT.setTag(KEY_TAG_COMPOUND, te);
                    blockTagList.appendTag(blockNBT);
                }
            }
        }
        NBTTagList paletteTag = new NBTTagList();
        for(IBlockState blockState : basicPalette) {
            paletteTag.appendTag(NBTUtil.writeBlockState(new NBTTagCompound(), blockState));
        }
        NBTTagList sizeTag = new NBTTagList();
        sizeTag.appendTag(new NBTTagInt(width));
        sizeTag.appendTag(new NBTTagInt(height));
        sizeTag.appendTag(new NBTTagInt(length));
        
        //save everything to the tag
        templateCompound.setTag(KEY_PALETTE, paletteTag);
        templateCompound.setTag(KEY_BLOCKS, blockTagList);
        templateCompound.setTag(KEY_ENTITIES, entities);
        templateCompound.setTag(KEY_SIZE, sizeTag);
        templateCompound.setString(KEY_AUTHOR, StructureLoaderSchematic.class.getName());
        templateCompound.setInteger(KEY_DATA_VERSION, DATA_VERSION);
        net.minecraftforge.fml.common.FMLCommonHandler.instance().getDataFixer().writeVersionData(templateCompound);
        
        return templateCompound;
    }

/***************************************************************************************************************************************/
    
    /**
     * helper class, this is a straight copy of {@link Template.BasicPalette}
     */
    static class BasicPalette implements Iterable<IBlockState>
    {
        public static final IBlockState DEFAULT_BLOCK_STATE = Blocks.AIR.getDefaultState();
        final ObjectIntIdentityMap<IBlockState> ids;
        private int lastId;

        private BasicPalette()
        {
            this.ids = new ObjectIntIdentityMap<IBlockState>(16);
        }

        public int idFor(IBlockState state)
        {
            int i = this.ids.get(state);

            if (i == -1)
            {
                i = this.lastId++;
                this.ids.put(state, i);
            }

            return i;
        }

        @Nullable
        public IBlockState stateFor(int id)
        {
            IBlockState iblockstate = (IBlockState)this.ids.getByValue(id);
            return iblockstate == null ? DEFAULT_BLOCK_STATE : iblockstate;
        }

        public Iterator<IBlockState> iterator()
        {
            return this.ids.iterator();
        }

        public void addMapping(IBlockState p_189956_1_, int p_189956_2_)
        {
            this.ids.put(p_189956_1_, p_189956_2_);
        }
    }

}
