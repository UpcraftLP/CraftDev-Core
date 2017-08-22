package core.upcraftlp.craftdev.api.templates;

public class Potion extends net.minecraft.potion.Potion {

    public Potion(String name, boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
        this.setRegistryName(name);
        this.setPotionName("potion." + name + ".name");
    }
}
