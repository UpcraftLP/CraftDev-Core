package core.upcraftlp.craftdev.API.templates;

import java.util.Random;

import core.upcraftlp.craftdev.common.CraftDevCore;
import core.upcraftlp.craftdev.init.CraftDevItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTab extends CreativeTabs {

    private ItemStack icon;
    private boolean hasSearchBar;
    private boolean displayRandom = false;
    public Random RANDOM = new Random();
    private int displayTick = 0;
    private int tempIndex = 0;
    private ItemStack tempDisplayStack;

    public CreativeTab(String label, boolean searchBarEnabled) {
        super(label + ".name");
        this.hasSearchBar = searchBarEnabled;
        if ( this.hasSearchBar ) this.setBackgroundImageName("item_search.png");
    }

    /**
     * Used to set a Tab's display icon. use null to display a random ITem from
     * the Tab's item list.
     * <p>
     * </p>
     * <b>Call this AFTER registering your renders, or the game will crash!</b>
     * You have been warned.
     * 
     * @param icon
     */
    @SideOnly(Side.CLIENT)
    public void setIconStack(ItemStack icon) {
        if ( icon == null ) {
            this.displayRandom = true;
            return;
        }
        icon.setCount(1);
        if ( icon.getItem() != null ) {
            this.icon = icon;
        }
        else {
            this.icon = new ItemStack(CraftDevItems.WRENCH);
            CraftDevCore.getLogger().println("Tried to register a null value as Item for a CreativeTab, defaulting to " + CraftDevItems.WRENCH.getRegistryName());
            CraftDevCore.getLogger().println("Details: " + this.toString());
        }
    }

    @Override
    public boolean hasSearchBar() {
        return this.hasSearchBar;
    }

    @Override
    public ItemStack getIconItemStack() {
        if ( this.displayTick % 60 == 0 || this.tempDisplayStack == null ) {
            this.updateDisplayStack();
        }
        this.displayTick++;
        return this.tempDisplayStack;
    }

    private void updateDisplayStack() {
        if ( !this.displayRandom ) {
            this.tempDisplayStack = this.icon;
        }
        else {
            NonNullList<ItemStack> itemStacks = NonNullList.create();
            this.displayAllRelevantItems(itemStacks);
            ItemStack toDisplay = itemStacks.get(tempIndex);
            this.tempDisplayStack = toDisplay;
            tempIndex++;
            if ( tempIndex >= itemStacks.size() )
                tempIndex = 0;
        }
    }

    @Override
    public ItemStack getTabIconItem() {
        return this.getIconItemStack();
    }

}
