package core.upcraftlp.craftdev.API.templates;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTab extends CreativeTabs {

    protected static final String BACKGROUND_IMAGE_SEARCHBAR = "item_search.png";
    
    private ItemStack icon = ItemStack.EMPTY;
    private boolean hasSearchBar = false;
    private boolean displayRandom = false;
    public Random RANDOM = new Random();
    private int tempIndex = 0;
    private ItemStack tempDisplayStack = ItemStack.EMPTY;

    /**
     * @param label
     * @param searchBarEnabled if the tab should have a search bar;<br/>this will also automatically set the background texture to <b>{@code item_search.png}</b>
     */
    public CreativeTab(String label, boolean searchBarEnabled) {
        super(label + ".name");
        this.hasSearchBar = searchBarEnabled;
        if (this.hasSearchBar) this.setBackgroundImageName(BACKGROUND_IMAGE_SEARCHBAR);
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
        if (icon == null || icon.isEmpty()) { // only place where you still have
            // to check for a null ItemStack
            // :P
            this.displayRandom = true;
            return;
        } else {
            icon.setCount(1);
            this.icon = icon;
        }
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
        if (!this.displayRandom) {
            this.tempDisplayStack = this.icon;
        } else {
            NonNullList<ItemStack> itemStacks = NonNullList.create();
            this.displayAllRelevantItems(itemStacks);
            ItemStack toDisplay = itemStacks.size() > tempIndex ? itemStacks.get(tempIndex) : ItemStack.EMPTY;
            this.tempDisplayStack = toDisplay;
            tempIndex++;
            if (tempIndex >= itemStacks.size()) tempIndex = 0;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack getTabIconItem() {
        return this.getIconItemStack();
    }

}
