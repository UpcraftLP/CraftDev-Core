package core.upcraftlp.craftdev.api.item;

import net.minecraft.inventory.EntityEquipmentSlot;

public class ItemArmor extends net.minecraft.item.ItemArmor {

    public ItemArmor(String name, ArmorMaterial materialIn, EntityEquipmentSlot equipmentSlot) {
        super(materialIn, equipmentSlot != EntityEquipmentSlot.LEGS ? 1 : 2, equipmentSlot);
        String prefixedName = name + "_" + equipmentSlot.getName();
        this.setRegistryName(prefixedName);
        this.setUnlocalizedName(prefixedName);
    }

}
