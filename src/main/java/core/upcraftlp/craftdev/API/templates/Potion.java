package core.upcraftlp.craftdev.API.templates;

public class Potion extends net.minecraft.potion.Potion {

	public Potion(String name, boolean isBadEffectIn, int liquidColorIn) {
		super(isBadEffectIn, liquidColorIn);
		this.setRegistryName(name);
		this.setPotionName(name);
	}
}
