package core.upcraftlp.craftdev.API.templates;

import net.minecraft.entity.EntityLivingBase;

public abstract class AbstractPotion extends net.minecraft.potion.Potion {

	public AbstractPotion(String name, boolean isBadEffectIn, int liquidColorIn) {
		super(isBadEffectIn, liquidColorIn);
		this.setRegistryName(name);
		this.setPotionName(name);
	}
	
	@Override
	public abstract void performEffect(EntityLivingBase entityLivingBaseIn, int p_76394_2_);

}
