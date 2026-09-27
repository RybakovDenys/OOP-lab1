package ua.lab.knight.equipment;

import org.junit.jupiter.api.Test;
import ua.lab.knight.EquipmentSlot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HelmetTest {

    @Test
    void createsHelmetWithGivenParameters() {
        Helmet helmet = new Helmet("Шолом", 2.0, 100, "Сталь", 15);

        assertEquals("Шолом", helmet.getName());
        assertEquals(2.0, helmet.getWeight());
        assertEquals(100, helmet.getPrice());
        assertEquals("Сталь", helmet.getMaterial());
        assertEquals(15, helmet.getDefenseBonus());
        assertEquals(EquipmentSlot.HELMET, helmet.getSlot());
        assertEquals("Armor", helmet.getCategory());
    }

    @Test
    void rejectsNonPositiveDefenseBonus() {
        assertThrows(IllegalArgumentException.class,
                () -> new Helmet("Шолом", 2.0, 100, "Сталь", 0));
    }

    @Test
    void rejectsBlankName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Helmet(" ", 2.0, 100, "Сталь", 10));
    }

    @Test
    void rejectsNonPositiveWeight() {
        assertThrows(IllegalArgumentException.class,
                () -> new Helmet("Шолом", 0, 100, "Сталь", 10));
    }
}
