package ua.lab.knight.equipment;

import org.junit.jupiter.api.Test;
import ua.lab.knight.EquipmentSlot;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChestplateTest {

    @Test
    void createsChestplateWithGivenParameters() {
        Chestplate chestplate = new Chestplate("Кіраса", 12.0, 400, "Сталь", 40);

        assertEquals(EquipmentSlot.CHESTPLATE, chestplate.getSlot());
        assertEquals("Armor", chestplate.getCategory());
        assertEquals(40, chestplate.getDefenseBonus());
        assertEquals("defense=+40", chestplate.describeCombatValue());
    }
}
