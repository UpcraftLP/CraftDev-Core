package core.upcraftlp.craftdev.API.templates;

import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import core.upcraftlp.craftdev.common.CraftDevCore;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTab extends CreativeTabs {

    protected static final String BACKGROUND_IMAGE_SEARCHBAR = "item_search.png";
    
    @Nonnull private ItemStack icon = ItemStack.EMPTY;
    private boolean hasSearchBar = false;
    private boolean displayRandom = false;
    protected Random RANDOM = new Random();
    private int tempIndex = 0;
    private ItemStack tempDisplayStack = ItemStack.EMPTY;

    /**
     * @param label the lang key for this tab, the final key will be {@code itemGroup.label.name}
     * @param searchBarEnabled if the tab should have a search bar;<br/>this will also automatically set the background texture to <b>{@code item_search.png}</b>
     */
    public CreativeTab(String label, boolean searchBarEnabled, ItemStack icon) {
        super(label + ".name");
        this.hasSearchBar = searchBarEnabled;
        if (this.hasSearchBar) this.setBackgroundImageName(BACKGROUND_IMAGE_SEARCHBAR);
        this.setIconStack(icon);
    }
    
    /**
     * @param label the lang key for this tab, the final key will be {@code itemGroup.label.name}
     */
    public CreativeTab(String label) {
        this(label, false, null);
    }
    
    /**
     * @param label the lang key for this tab, the final key will be {@code itemGroup.label.name}
     */
    public CreativeTab(String label, ItemStack icon) {
        this(label, false, icon);
    }
    

    /**
     * Used to set a CreativeTab's display icon. use null to display a random Item from
     * the Tab's item list.
     * 
     * @param icon
     */
    public void setIconStack(@Nullable ItemStack icon) {
        if(icon == null) icon = ItemStack.EMPTY; //only place where you still have to check for a null ItemStack :P
        icon.setCount(1);
        this.icon = icon;
        if (icon.isEmpty()) this.displayRandom = true;
    }

    @Override
    public boolean hasSearchBar() {
        return this.hasSearchBar;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack getIconItemStack() {
        if (Minecraft.getSystemTime() % 60 == 0 || this.tempDisplayStack.isEmpty()) {
            this.updateDisplayStack();
        }
        return this.tempDisplayStack;
    }

    private void updateDisplayStack() {
        if (this.displayRandom) {
            NonNullList<ItemStack> itemStacks = NonNullList.create();
            this.displayAllRelevantItems(itemStacks);
            ItemStack toDisplay = itemStacks.size() > tempIndex ? itemStacks.get(tempIndex) : ItemStack.EMPTY;
            this.tempDisplayStack = toDisplay;
            tempIndex++;
            if (tempIndex >= itemStacks.size()) tempIndex = 0;
        } else {
            if(this.icon.isEmpty()) {
                CraftDevCore.getLogger().println("found empty Itemstack for CreativeTab " + this.getTabLabel() + ", defaulting to " + Items.DIAMOND.getRegistryName());
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
