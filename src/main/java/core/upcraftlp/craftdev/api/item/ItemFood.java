package core.upcraftlp.craftdev.api.templates;

public class ItemFood extends net.minecraft.item.ItemFood {

    public ItemFood(String name, int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
    }

}
