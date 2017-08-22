package core.upcraftlp.craftdev.api.event;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;


/**
 * fired on the {@link MinecraftForge#EVENT_BUS} when trying to determine the enchantability of an ItemStack.
 * @author UpcraftLP
 *
 */
@Cancelable
public class EnchantabilityEvent extends Event {

    public EnchantabilityEvent(ItemStack stack, int enchantabilityIn) {
        this.stack = stack;
        this.enchantabiltiy = enchantabilityIn;
    }
    
    private ItemStack stack;
    private int enchantabiltiy;
    
    public int getEnchantability() {
        return this.enchantabiltiy;
    }
    
    public void setEnchantability(int enchantabilityIn) {
        this.enchantabiltiy = enchantabilityIn;
    }
    
    public ItemStack getItemStack() {
        return this.stack;
    }
    
}
