package ua.lab.knight.equipment;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EquipmentTest {

    @Test
    void equipmentIsComparableByWeight() {
        Equipment light = new Boots("Boots", 2.0, 70, "Leather", 8);
        Equipment heavy = new Chestplate("Cuirass", 12.0, 400, "Steel", 40);

        assertTrue(light.compareTo(heavy) < 0);
        assertTrue(heavy.compareTo(light) > 0);
    }

    @Test
    void equalsAndHashCodeConsiderAllFields() {
        Equipment a = new Helmet("Helmet", 2.0, 100, "Steel", 15);
        Equipment b = new Helmet("Helmet", 2.0, 100, "Steel", 15);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void toStringContainsKeyInformation() {
        Equipment helmet = new Helmet("Helmet", 2.0, 100, "Steel", 15);
        String text = helmet.toString();

        assertTrue(text.contains("Helmet"));
        assertTrue(text.contains("HELMET"));
    }

    @Test
    void polymorphicListCanMixDifferentEquipmentTypes() {
        List<Equipment> items = List.of(
                new Helmet("Helmet", 2.0, 100, "Steel", 15),
                new Weapon("Sword", 3.5, 250, "Steel", 45, WeaponType.SWORD),
                new Shield("Shield", 6.0, 180, "Wood", 30)
        );

        double totalPrice = items.stream().mapToDouble(Equipment::getPrice).sum();
        assertEquals(530.0, totalPrice, 0.0001);
    }
}
