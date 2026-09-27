package ua.lab.knight.equipment;

import ua.lab.knight.EquipmentSlot;

/**
 * A melee weapon wielded by the knight. Extends {@link Equipment} directly
 * (rather than {@link Armor}) since a weapon is not a piece of body armor -
 * inheritance is used here only where it reflects a real "is-a" relationship.
 */
public class Weapon extends Equipment {

    private final int damage;
    private final WeaponType weaponType;

    public Weapon(String name, double weight, double price, String material,
                  int damage, WeaponType weaponType) {
        super(name, weight, price, material);
        if (damage <= 0) {
            throw new IllegalArgumentException("damage must be a positive number");
        }
        this.damage = damage;
        this.weaponType = weaponType;
    }

    public int getDamage() {
        return damage;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    @Override
    public String getCategory() {
        return "Weapon";
    }

    @Override
    public EquipmentSlot getSlot() {
        return EquipmentSlot.WEAPON;
    }

    @Override
    public String describeCombatValue() {
        return "type=" + weaponType + ", damage=" + damage;
    }
}
