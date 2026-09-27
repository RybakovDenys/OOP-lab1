package ua.lab.knight.equipment;

import ua.lab.knight.EquipmentSlot;

/** A chestplate (cuirass) - armor piece protecting the knight's torso. */
public class Chestplate extends Armor {

    public Chestplate(String name, double weight, double price, String material, int defenseBonus) {
        super(name, weight, price, material, defenseBonus);
    }

    @Override
    public EquipmentSlot getSlot() {
        return EquipmentSlot.CHESTPLATE;
    }
}
