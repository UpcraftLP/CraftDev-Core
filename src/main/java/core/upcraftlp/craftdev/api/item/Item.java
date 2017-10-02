package core.upcraftlp.craftdev.api.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Item extends net.minecraft.item.Item {

    private int subItemCount = 0;

    public Item(String name) {
        super();
        this.setUnlocalizedName(name);
        this.setRegistryName(name);

    }

    public void setSubItems(int count) {
        this.subItemCount = count;
        this.setHasSubtypes(true);
    }

    public int getSubItemCount() {
        return this.subItemCount;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if ( this.getSubItemCount() > 0 ) {
            for ( int i = 0; i < this.getSubItemCount(); i++ )
                items.add(new ItemStack(this, 1, i));
        }
        else {
            super.getSubItems(tab, items);
        }
    }

}
