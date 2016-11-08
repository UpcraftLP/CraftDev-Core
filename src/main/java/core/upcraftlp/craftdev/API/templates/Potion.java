package core.upcraftlp.craftdev.API.templates;

import net.minecraft.entity.EntityLivingBase;

public class Potion extends net.minecraft.potion.Potion {

	public Potion(String name, boolean isBadEffectIn, int liquidColorIn) {
		super(isBadEffectIn, liquidColorIn);
		this.setRegistryName(name);
		this.setPotionName(name);
	}
	
	@Override
	public void performEffect(EntityLivingBase entityLivingBaseIn, int p_76394_2_){
	}

}
