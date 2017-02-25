package core.upcraftlp.craftdev.API.templates;

import core.upcraftlp.craftdev.API.util.IItemPropertyGetterFix;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemBow extends net.minecraft.item.ItemBow {

    public ItemBow(String name, String modid) {
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.addPropertyOverride(new ResourceLocation(modid, "pull"),
                IItemPropertyGetterFix.create((stack, worldIn, entityIn) -> {
                    if ( entityIn == null )
                        return 0.0f;

                    final ItemStack activeItemStack = entityIn.getActiveItemStack();
                    if ( activeItemStack != null && activeItemStack.getItem() instanceof ItemBow ) {
                        return (stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0f;
                    }

                    return 0.0f;
                }));
    }
}
