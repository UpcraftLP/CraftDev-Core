package core.upcraftlp.craftdev.api.templates;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

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
    public void getSubItems(net.minecraft.item.Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if ( this.getSubItemCount() > 0 ) {
            for ( int i = 0; i < this.getSubItemCount(); i++ )
                subItems.add(new ItemStack(this, 1, i));
        }
        else {
            super.getSubItems(itemIn, tab, subItems);
        }
    }
}
