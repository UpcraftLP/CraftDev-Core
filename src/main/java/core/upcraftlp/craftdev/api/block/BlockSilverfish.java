package core.upcraftlp.craftdev.api.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockSilverfish extends Block {

    protected net.minecraft.block.Block drop;

    public BlockSilverfish(net.minecraft.block.Block drop) {
        super(drop.getUnlocalizedName().substring(5) + "_silverfish", Material.CLAY, true);
        this.setUnlocalizedName(drop.getUnlocalizedName().substring(5));
        this.setSoundType(SoundType.STONE);
        this.setHardness(2.0f);
        this.setResistance(30.0f);
        this.setDrop(drop);
    }

    public void setDrop(net.minecraft.block.Block drop) {
        this.drop = drop;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, net.minecraft.entity.player.EntityPlayer player, List<String> tooltip, boolean advanced) {
        tooltip.add(TextFormatting.GRAY + I18n.format("desc.silverfish.name"));
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        if ( !worldIn.isRemote && worldIn.getGameRules().getBoolean("doTileDrops") ) {
            if ( chance > 0.0f ) {
                EntityItem item = new EntityItem(worldIn, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, new ItemStack(this.drop, 1));
                worldIn.spawnEntity(item);
            }
            if ( worldIn.getDifficulty() != EnumDifficulty.PEACEFUL ) {
                EntitySilverfish entitysilverfish = new EntitySilverfish(worldIn);
                entitysilverfish.setLocationAndAngles((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, 0.0F, 0.0F);
                worldIn.spawnEntity(entitysilverfish);
                entitysilverfish.spawnExplosionParticle();
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this.drop);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        if ( worldIn.isRemote || player.capabilities.isCreativeMode )
            return;
        player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 160));
    }
}
