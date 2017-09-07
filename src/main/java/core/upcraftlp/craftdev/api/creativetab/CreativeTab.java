package core.upcraftlp.craftdev.api.creativetab;

import core.upcraftlp.craftdev.common.CraftDevCore;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class CreativeTab extends CreativeTabs {

    protected static final String BACKGROUND_IMAGE_SEARCHBAR = "item_search.png";

    private ItemStack icon = ItemStack.EMPTY;
    private boolean hasSearchBar = false;
    private boolean displayRandom = true;
    protected final Random RANDOM = new Random();
    private int tempIndex = 0;
    private ItemStack tempDisplayStack = ItemStack.EMPTY;

    /**
     * @param label the lang key for this tab, the final key will be {@code itemGroup.label.name}
     * @param searchBarEnabled if the tab should have a search bar;<br/>this will also automatically set the background texture to <b>{@code item_search.png}</b>
     */
    public CreativeTab(String label, boolean searchBarEnabled) {
        super(label + ".name");
        this.hasSearchBar = searchBarEnabled;
        if (searchBarEnabled) this.setBackgroundImageName(BACKGROUND_IMAGE_SEARCHBAR);
    }
    
    /**
     * @param label the lang key for this tab, the final key will be {@code itemGroup.label.name}
     */
    public CreativeTab(String label) {
        this(label, false);
    }    

    /**
     * Used to set a CreativeTab's display icon. use {@link ItemStack#EMPTY} to display a random Item from
     * the Tab's item list.
     */
    public void setIconStack(ItemStack icon) {
        if (icon.isEmpty()) this.displayRandom = true;
		else {
			this.displayRandom = false;
			icon.setCount(1);
		}
		this.icon = icon;
    }

    @Override
    public boolean hasSearchBar() {
        return this.hasSearchBar;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack getIconItemStack() {
			if(this.displayRandom) {
				if (Minecraft.getSystemTime() % 120 == 0) {
				this.updateDisplayStack();
			}
		return this.tempDisplayStack;
		}
		else return this.icon;
    }

    @SideOnly(Side.CLIENT)
    private void updateDisplayStack() {
        if (this.displayRandom) {
            NonNullList<ItemStack> itemStacks = NonNullList.create();
            this.displayAllRelevantItems(itemStacks);
            this.tempDisplayStack = !itemStacks.isEmpty() ? itemStacks.get(tempIndex) : ItemStack.EMPTY;
			if (++tempIndex >= itemStacks.size()) tempIndex = 0;
        } else {
            if(this.icon.isEmpty()) {
                CraftDevCore.log.warn("found empty Itemstack for CreativeTab " + this.getTabLabel() + ", defaulting to " + Items.DIAMOND.getRegistryName());
                this.tempDisplayStack = new ItemStack(Items.DIAMOND);
            }
            this.tempDisplayStack = this.icon;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack getTabIconItem() {
        return this.getIconItemStack();
    }

}
