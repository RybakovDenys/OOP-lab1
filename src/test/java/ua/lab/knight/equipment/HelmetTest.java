package ua.lab.knight.equipment;

import org.junit.jupiter.api.Test;
import ua.lab.knight.EquipmentSlot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HelmetTest {

    @Test
    void createsHelmetWithGivenParameters() {
        Helmet helmet = new Helmet("Helmet", 2.0, 100, "Steel", 15);

        assertEquals("Helmet", helmet.getName());
        assertEquals(2.0, helmet.getWeight());
        assertEquals(100, helmet.getPrice());
        assertEquals("Steel", helmet.getMaterial());
        assertEquals(15, helmet.getDefenseBonus());
        assertEquals(EquipmentSlot.HELMET, helmet.getSlot());
        assertEquals("Armor", helmet.getCategory());
    }

    @Test
    void rejectsNonPositiveDefenseBonus() {
        assertThrows(IllegalArgumentException.class,
                () -> new Helmet("Helmet", 2.0, 100, "Steel", 0));
    }

    @Test
    void rejectsBlankName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Helmet(" ", 2.0, 100, "Steel", 10));
    }

    @Test
    void rejectsNonPositiveWeight() {
        assertThrows(IllegalArgumentException.class,
                () -> new Helmet("Helmet", 0, 100, "Steel", 10));
    }
}
