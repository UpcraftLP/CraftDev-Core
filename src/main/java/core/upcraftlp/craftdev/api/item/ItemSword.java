package core.upcraftlp.craftdev.api.item;

public class ItemSword extends net.minecraft.item.ItemSword {

    public ItemSword(String name, ToolMaterial material) {
        super(material);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setFull3D();
    }

}
