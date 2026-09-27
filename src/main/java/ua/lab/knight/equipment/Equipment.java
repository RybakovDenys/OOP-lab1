package ua.lab.knight.equipment;

import ua.lab.knight.EquipmentSlot;

import java.util.Objects;

/**
 * Base class for every piece of a knight's ammunition (equipment).
 * <p>
 * Encapsulates the properties that are common to all equipment items:
 * name, weight, price and the material it is made of. Concrete equipment
 * categories (armor pieces, weapons, shields) extend this class and add
 * their own specific characteristics, which demonstrates inheritance and
 * polymorphism across the hierarchy.
 */
public abstract class Equipment implements Comparable<Equipment> {

    private final String name;
    private final double weight;
    private final double price;
    private final String material;

    protected Equipment(String name, double weight, double price, String material) {
        this.name = validateName(name);
        this.weight = validatePositive(weight, "weight");
        this.price = validatePositive(price, "price");
        this.material = validateName(material);
    }

    private static String validateName(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Name/material must not be null or blank");
        }
        return value;
    }

    private static double validatePositive(double value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be a positive number");
        }
        return value;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public double getPrice() {
        return price;
    }

    public String getMaterial() {
        return material;
    }

    /**
     * @return a human-readable category name of this equipment item,
     *         e.g. "Weapon", "Armor" or "Shield".
     */
    public abstract String getCategory();

    /**
     * @return the equipment slot that this item occupies on the knight.
     */
    public abstract EquipmentSlot getSlot();

    /**
     * @return a short description of the item's key combat characteristic,
     *         implemented differently by each subclass (polymorphism).
     */
    public abstract String describeCombatValue();

    @Override
    public int compareTo(Equipment other) {
        return Double.compare(this.weight, other.weight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Equipment)) {
            return false;
        }
        Equipment equipment = (Equipment) o;
        return Double.compare(weight, equipment.weight) == 0
                && Double.compare(price, equipment.price) == 0
                && name.equals(equipment.name)
                && material.equals(equipment.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weight, price, material);
    }

    @Override
    public String toString() {
        return String.format(
                "%s{name='%s', slot=%s, weight=%.2f kg, price=%.2f, material='%s', %s}",
                getCategory(), name, getSlot(), weight, price, material, describeCombatValue());
    }
}
