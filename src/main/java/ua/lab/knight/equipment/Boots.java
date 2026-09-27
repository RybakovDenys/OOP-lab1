package ua.lab.knight.equipment;

import ua.lab.knight.EquipmentSlot;

/** Boots - armor piece protecting the knight's feet. */
public class Boots extends Armor {

    public Boots(String name, double weight, double price, String material, int defenseBonus) {
        super(name, weight, price, material, defenseBonus);
    }

    @Override
    public EquipmentSlot getSlot() {
        return EquipmentSlot.BOOTS;
    }
}
