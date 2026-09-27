package ua.lab.knight.equipment;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EquipmentTest {

    @Test
    void equipmentIsComparableByWeight() {
        Equipment light = new Boots("Чоботи", 2.0, 70, "Шкіра", 8);
        Equipment heavy = new Chestplate("Кіраса", 12.0, 400, "Сталь", 40);

        assertTrue(light.compareTo(heavy) < 0);
        assertTrue(heavy.compareTo(light) > 0);
    }

    @Test
    void equalsAndHashCodeConsiderAllFields() {
        Equipment a = new Helmet("Шолом", 2.0, 100, "Сталь", 15);
        Equipment b = new Helmet("Шолом", 2.0, 100, "Сталь", 15);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void toStringContainsKeyInformation() {
        Equipment helmet = new Helmet("Шолом", 2.0, 100, "Сталь", 15);
        String text = helmet.toString();

        assertTrue(text.contains("Шолом"));
        assertTrue(text.contains("HELMET"));
    }

    @Test
    void polymorphicListCanMixDifferentEquipmentTypes() {
        List<Equipment> items = List.of(
                new Helmet("Шолом", 2.0, 100, "Сталь", 15),
                new Weapon("Меч", 3.5, 250, "Сталь", 45, WeaponType.SWORD),
                new Shield("Щит", 6.0, 180, "Дерево", 30)
        );

        double totalPrice = items.stream().mapToDouble(Equipment::getPrice).sum();
        assertEquals(530.0, totalPrice, 0.0001);
    }
}
