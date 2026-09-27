package ua.lab.knight.equipment;

import org.junit.jupiter.api.Test;
import ua.lab.knight.EquipmentSlot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShieldTest {

    @Test
    void createsShieldWithGivenParameters() {
        Shield shield = new Shield("Щит", 6.0, 180, "Дерево", 30);

        assertEquals("Shield", shield.getCategory());
        assertEquals(EquipmentSlot.SHIELD, shield.getSlot());
        assertEquals(30, shield.getBlockChancePercent());
    }

    @Test
    void rejectsBlockChanceOutOfRange() {
        assertThrows(IllegalArgumentException.class,
                () -> new Shield("Щит", 6.0, 180, "Дерево", 0));
        assertThrows(IllegalArgumentException.class,
                () -> new Shield("Щит", 6.0, 180, "Дерево", 101));
    }
}
