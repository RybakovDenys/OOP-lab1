package ua.lab.knight.equipment;

import ua.lab.knight.EquipmentSlot;

/** Gauntlets - armor piece protecting the knight's hands. */
public class Gauntlets extends Armor {

    public Gauntlets(String name, double weight, double price, String material, int defenseBonus) {
        super(name, weight, price, material, defenseBonus);
    }

    @Override
    public EquipmentSlot getSlot() {
        return EquipmentSlot.GAUNTLETS;
    }
}
