package core.upcraftlp.craftdev.api.block;

import com.google.common.collect.Lists;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

/**
 * @author UpcraftLP
 */
@SuppressWarnings("deprecation")
public abstract class BlockLeaves extends Block implements IShearable {

    public static final IProperty<Boolean> DECAYABLE = PropertyBool.create("decayable");
    public static final IProperty<Boolean> CHECK_DECAY = PropertyBool.create("check_decay");
    private int[] surroundings;

    public BlockLeaves(String name) {
        super("leaves_" + name, Material.LEAVES, true);
        this.setDefaultState(this.getDefaultState().withProperty(DECAYABLE, false).withProperty(CHECK_DECAY, false));
        this.setTickRandomly(true);
        this.setHardness(0.2f);
        this.setLightOpacity(1);
        this.setSoundType(SoundType.PLANT);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
                                  EntityPlayer player) {
        return new ItemStack(this);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        int k = pos.getX();
        int l = pos.getY();
        int i1 = pos.getZ();

        if (worldIn.isAreaLoaded(new BlockPos(k - 2, l - 2, i1 - 2), new BlockPos(k + 2, l + 2, i1 + 2)))
        {
            for (int j1 = -1; j1 <= 1; ++j1)
            {
                for (int k1 = -1; k1 <= 1; ++k1)
                {
                    for (int l1 = -1; l1 <= 1; ++l1)
                    {
                        BlockPos blockpos = pos.add(j1, k1, l1);
                        IBlockState iblockstate = worldIn.getBlockState(blockpos);
                        if (iblockstate.getBlock().isLeaves(iblockstate, worldIn, blockpos)) iblockstate.getBlock().beginLeavesDecay(iblockstate, worldIn, blockpos);
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (worldIn.isRainingAt(pos.up()) && !worldIn.getBlockState(pos.down()).isOpaqueCube() && rand.nextInt(15) == 1)
        {
            double d0 = (double)((float)pos.getX() + rand.nextFloat());
            double d1 = (double)pos.getY() - 0.05D;
            double d2 = (double)((float)pos.getZ() + rand.nextFloat());
            worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote)
        {
            if (state.getValue(CHECK_DECAY) && state.getValue(DECAYABLE))
            {
                int k = pos.getX();
                int l = pos.getY();
                int i1 = pos.getZ();

                if (this.surroundings == null)
                {
                    this.surroundings = new int[32768];
                }

                if (worldIn.isAreaLoaded(new BlockPos(k - 5, l - 5, i1 - 5), new BlockPos(k + 5, l + 5, i1 + 5)))
                {
                    BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

                    for (int i2 = -4; i2 <= 4; ++i2)
                    {
                        for (int j2 = -4; j2 <= 4; ++j2)
                        {
                            for (int k2 = -4; k2 <= 4; ++k2)
                            {
                                IBlockState iblockstate = worldIn.getBlockState(mutableBlockPos.setPos(k + i2, l + j2, i1 + k2));
                                net.minecraft.block.Block block = iblockstate.getBlock();

                                if (!block.canSustainLeaves(iblockstate, worldIn, mutableBlockPos.setPos(k + i2, l + j2, i1 + k2)))
                                {
                                    if (block.isLeaves(iblockstate, worldIn, mutableBlockPos.setPos(k + i2, l + j2, i1 + k2)))
                                    {
                                        this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = -2;
                                    }
                                    else
                                    {
                                        this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = -1;
                                    }
                                }
                                else
                                {
                                    this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = 0;
                                }
                            }
                        }
                    }

                    for (int i3 = 1; i3 <= 4; ++i3)
                    {
                        for (int j3 = -4; j3 <= 4; ++j3)
                        {
                            for (int k3 = -4; k3 <= 4; ++k3)
                            {
                                for (int l3 = -4; l3 <= 4; ++l3)
                                {
                                    if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16] == i3 - 1)
                                    {
                                        if (this.surroundings[(j3 + 16 - 1) * 1024 + (k3 + 16) * 32 + l3 + 16] == -2)
                                        {
                                            this.surroundings[(j3 + 16 - 1) * 1024 + (k3 + 16) * 32 + l3 + 16] = i3;
                                        }

                                        if (this.surroundings[(j3 + 16 + 1) * 1024 + (k3 + 16) * 32 + l3 + 16] == -2)
                                        {
                                            this.surroundings[(j3 + 16 + 1) * 1024 + (k3 + 16) * 32 + l3 + 16] = i3;
                                        }

                                        if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16 - 1) * 32 + l3 + 16] == -2)
                                        {
                                            this.surroundings[(j3 + 16) * 1024 + (k3 + 16 - 1) * 32 + l3 + 16] = i3;
                                        }

                                        if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16 + 1) * 32 + l3 + 16] == -2)
                                        {
                                            this.surroundings[(j3 + 16) * 1024 + (k3 + 16 + 1) * 32 + l3 + 16] = i3;
                                        }

                                        if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + (l3 + 16 - 1)] == -2)
                                        {
                                            this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + (l3 + 16 - 1)] = i3;
                                        }

                                        if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16 + 1] == -2)
                                        {
                                            this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16 + 1] = i3;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                int l2 = this.surroundings[16912];

                if (l2 >= 0)
                {
                    worldIn.setBlockState(pos, state.withProperty(CHECK_DECAY, false), 4);
                }
                else
                {
                    this.destroy(worldIn, pos);
                }
            }
        }
    }

    private void destroy(World worldIn, BlockPos pos)
    {
        this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
        worldIn.setBlockToAir(pos);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return !Minecraft.isFancyGraphicsEnabled();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, DECAYABLE, CHECK_DECAY);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        boolean decayable = (meta >>> 1) == 1;
        boolean check_decay = (meta & 0b01) == 1;
        return this.getDefaultState().withProperty(DECAYABLE, decayable).withProperty(CHECK_DECAY, check_decay);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        byte meta = (byte) (state.getValue(DECAYABLE) ? 1 : 0);
        meta = (byte) (meta << 1);
        byte check_decay = (byte) (state.getValue(CHECK_DECAY) ? 1 : 0);
        return (meta | check_decay);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        return Minecraft.isFancyGraphicsEnabled() ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        List<ItemStack> drops = Lists.newArrayList(new ItemStack(this));
        int quantity = this.quantityDropped(RANDOM);
        if(quantity > 0 && !this.getSapling().isEmpty()) {
            ItemStack sapling = this.getSapling();
            sapling.setCount(quantity);
            drops.add(sapling);
        }
        return drops;
    }

    @Nonnull
    public ItemStack getSapling() {
        return ItemStack.EMPTY;
    }

    @Override
    public void beginLeavesDecay(IBlockState state, World world, BlockPos pos) {
        if (!state.getValue(CHECK_DECAY))
        {
            world.setBlockState(pos, state.withProperty(CHECK_DECAY, true), 4);
        }
    }

    @Override
    public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return (Minecraft.isFancyGraphicsEnabled() || blockAccess.getBlockState(pos.offset(side)).getBlock() != this) && super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    @Override
    public boolean causesSuffocation(IBlockState state) {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) || worldIn.getBlockState(pos).getBlock().canBeReplacedByLeaves(worldIn.getBlockState(pos), worldIn, pos);
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type) {
        return false;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return !this.getSapling().isEmpty() ? this.getSapling().getItem() : Items.AIR;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return this.getSapling().getMetadata();
    }

    /**
     * @return how many saplings a single block should drop (on average), capped at {@code 1.0F}
     */
    public float getSaplingProbability() {
        return 0.4F; //40%
    }

    @Override
    public int quantityDropped(Random random) { //FIXME allow chances > 1.0F
        return random.nextFloat() < this.getSaplingProbability() ? 1 : 0;
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack(this);
    }


}
