package core.upcraftlp.craftdev.items;

import java.util.List;

import core.upcraftlp.craftdev.API.templates.Item;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ItemUniWrench extends Item {

    public ItemUniWrench() {
        super("universal_wrench");
        // TODO: wrench behaviour
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        tooltip.add(TextFormatting.RED + "WIP!");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
            EnumFacing facing, float hitX, float hitY, float hitZ) {
        if ( worldIn.isRemote ) {
            TileEntity te = worldIn.getTileEntity(pos);
            IBlockState state = worldIn.getBlockState(pos);
            if ( !player.isSneaking() ) {
                state = state.withRotation(Rotation.CLOCKWISE_90);
                if ( te != null ) te.rotate(Rotation.CLOCKWISE_90);
            }
            else if ( te != null ) {
                state.getBlock().harvestBlock(worldIn, player, pos, state, te, player.getHeldItem(hand));
            }
            return EnumActionResult.SUCCESS;
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return false;
    }

}
