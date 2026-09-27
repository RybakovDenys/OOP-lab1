package ua.lab.knight.equipment;

import ua.lab.knight.EquipmentSlot;

/** Leggings - armor piece protecting the knight's legs. */
public class Leggings extends Armor {

    public Leggings(String name, double weight, double price, String material, int defenseBonus) {
        super(name, weight, price, material, defenseBonus);
    }

    @Override
    public EquipmentSlot getSlot() {
        return EquipmentSlot.LEGGINGS;
    }
}
