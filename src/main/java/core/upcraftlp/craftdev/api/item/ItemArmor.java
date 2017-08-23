package core.upcraftlp.craftdev.api.templates;

import net.minecraft.inventory.EntityEquipmentSlot;

public class ItemArmor extends net.minecraft.item.ItemArmor {

    private String prefixedName;

    public ItemArmor(String name, ArmorMaterial materialIn, EntityEquipmentSlot equipmentSlot) {
        super(materialIn, equipmentSlot != EntityEquipmentSlot.LEGS ? 1 : 2, equipmentSlot);
        this.prefixedName = name + "_" + equipmentSlot.getName();
        this.setRegistryName(this.prefixedName);
        this.setUnlocalizedName(this.prefixedName);
    }

}
