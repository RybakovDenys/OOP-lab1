package ua.lab.knight.equipment;

import ua.lab.knight.EquipmentSlot;

/** A helmet - armor piece protecting the knight's head. */
public class Helmet extends Armor {

    public Helmet(String name, double weight, double price, String material, int defenseBonus) {
        super(name, weight, price, material, defenseBonus);
    }

    @Override
    public EquipmentSlot getSlot() {
        return EquipmentSlot.HELMET;
    }
}
