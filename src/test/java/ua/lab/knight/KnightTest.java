package ua.lab.knight;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.lab.knight.equipment.Boots;
import ua.lab.knight.equipment.Chestplate;
import ua.lab.knight.equipment.Equipment;
import ua.lab.knight.equipment.Helmet;
import ua.lab.knight.equipment.Shield;
import ua.lab.knight.equipment.Weapon;
import ua.lab.knight.equipment.WeaponType;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KnightTest {

    private Knight knight;
    private Helmet helmet;
    private Chestplate chestplate;
    private Boots boots;
    private Weapon sword;
    private Shield shield;

    @BeforeEach
    void setUp() {
        knight = new Knight("Сер Едмунд");
        helmet = new Helmet("Шолом", 2.0, 150, "Сталь", 15);
        chestplate = new Chestplate("Кіраса", 12.0, 420, "Сталь", 40);
        boots = new Boots("Чоботи", 2.5, 70, "Шкіра", 8);
        sword = new Weapon("Меч", 3.5, 260, "Сталь", 45, WeaponType.SWORD);
        shield = new Shield("Щит", 6.0, 190, "Дерево", 30);
    }

    @Test
    void rejectsBlankKnightName() {
        assertThrows(IllegalArgumentException.class, () -> new Knight(" "));
    }

    @Test
    void equipAddsItemToCorrectSlot() {
        knight.equip(helmet);

        Optional<Equipment> equipped = knight.getEquippedItem(EquipmentSlot.HELMET);
        assertTrue(equipped.isPresent());
        assertEquals(helmet, equipped.get());
    }

    @Test
    void equippingIntoOccupiedSlotReplacesPreviousItem() {
        knight.equip(helmet);
        Helmet betterHelmet = new Helmet("Кращий шолом", 2.2, 200, "Сталь", 20);

        Optional<Equipment> previous = knight.equip(betterHelmet);

        assertTrue(previous.isPresent());
        assertEquals(helmet, previous.get());
        assertEquals(betterHelmet, knight.getEquippedItem(EquipmentSlot.HELMET).orElseThrow());
        assertEquals(1, knight.getEquippedItems().size());
    }

    @Test
    void unequipRemovesItemFromSlot() {
        knight.equip(helmet);

        Optional<Equipment> removed = knight.unequip(EquipmentSlot.HELMET);

        assertTrue(removed.isPresent());
        assertFalse(knight.getEquippedItem(EquipmentSlot.HELMET).isPresent());
    }

    @Test
    void calculateTotalCostSumsAllEquippedItems() {
        knight.equip(helmet);
        knight.equip(chestplate);
        knight.equip(sword);

        assertEquals(150 + 420 + 260, knight.calculateTotalCost(), 0.0001);
    }

    @Test
    void calculateTotalWeightSumsAllEquippedItems() {
        knight.equip(helmet);
        knight.equip(boots);

        assertEquals(2.0 + 2.5, knight.calculateTotalWeight(), 0.0001);
    }

    @Test
    void sortByWeightAscendingOrdersItemsFromLightestToHeaviest() {
        knight.equip(chestplate); // 12.0
        knight.equip(helmet);     // 2.0
        knight.equip(boots);      // 2.5

        List<Equipment> sorted = knight.sortByWeightAscending();

        assertEquals(helmet, sorted.get(0));
        assertEquals(boots, sorted.get(1));
        assertEquals(chestplate, sorted.get(2));
    }

    @Test
    void sortByWeightDescendingOrdersItemsFromHeaviestToLightest() {
        knight.equip(chestplate); // 12.0
        knight.equip(helmet);     // 2.0
        knight.equip(boots);      // 2.5

        List<Equipment> sorted = knight.sortByWeightDescending();

        assertEquals(chestplate, sorted.get(0));
        assertEquals(boots, sorted.get(1));
        assertEquals(helmet, sorted.get(2));
    }

    @Test
    void findByPriceRangeReturnsOnlyItemsWithinRangeInclusive() {
        knight.equip(helmet);     // 150
        knight.equip(chestplate); // 420
        knight.equip(sword);      // 260
        knight.equip(shield);     // 190

        List<Equipment> found = knight.findByPriceRange(150, 260);

        assertEquals(3, found.size());
        assertTrue(found.contains(helmet));
        assertTrue(found.contains(sword));
        assertTrue(found.contains(shield));
        assertFalse(found.contains(chestplate));
    }

    @Test
    void findByPriceRangeReturnsEmptyListWhenNothingMatches() {
        knight.equip(helmet);

        List<Equipment> found = knight.findByPriceRange(1000, 2000);

        assertTrue(found.isEmpty());
    }

    @Test
    void findByPriceRangeRejectsInvertedRange() {
        assertThrows(IllegalArgumentException.class, () -> knight.findByPriceRange(200, 100));
    }
}
