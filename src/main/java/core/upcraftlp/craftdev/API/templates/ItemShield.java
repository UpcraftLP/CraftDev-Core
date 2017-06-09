package core.upcraftlp.craftdev.API.templates;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;

import javax.annotation.Nullable;
import java.util.List;

/**
 * (c)2017 UpcraftLP
 */
public class ItemShield extends Item {

    private int maxBlockingTime;

    public ItemShield(String name, int durability, int maxBlockingTime) {
        super(name);
        this.setMaxDamage(durability);
        this.maxBlockingTime = maxBlockingTime;
        this.addPropertyOverride(new ResourceLocation(this.getRegistryName().getResourceDomain(),"blocking"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, net.minecraft.item.ItemArmor.DISPENSER_BEHAVIOR);
    }

    public ItemShield(String name, int durability) {
        this(name, durability, 72000);
    }

    public ItemShield(String name) {
        this(name, 336);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BLOCK;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return this.maxBlockingTime;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        if (stack.getSubCompound("BlockEntityTag") != null)
        {
            EnumDyeColor enumdyecolor = TileEntityBanner.getColor(stack);
            return I18n.format("item.shield." + enumdyecolor.getUnlocalizedName() + ".name");
        }
        else
        {
            return I18n.format("item.shield.name");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        ItemBanner.appendHoverTextFromTileEntityTag(stack, tooltip);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return new ActionResult(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        Block b = Block.getBlockFromItem(repair.getItem());
        return b != null && (b instanceof BlockPlanks || b.getDefaultState().getMaterial() == Material.WOOD || super.getIsRepairable(toRepair, repair));
    }
}
