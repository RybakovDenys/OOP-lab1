package ua.lab.knight.equipment;

import org.junit.jupiter.api.Test;
import ua.lab.knight.EquipmentSlot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WeaponTest {

    @Test
    void createsWeaponWithGivenParameters() {
        Weapon sword = new Weapon("Sword", 3.5, 250, "Steel", 45, WeaponType.SWORD);

        assertEquals("Weapon", sword.getCategory());
        assertEquals(EquipmentSlot.WEAPON, sword.getSlot());
        assertEquals(45, sword.getDamage());
        assertEquals(WeaponType.SWORD, sword.getWeaponType());
        assertEquals("type=SWORD, damage=45", sword.describeCombatValue());
    }

    @Test
    void rejectsNonPositiveDamage() {
        assertThrows(IllegalArgumentException.class,
                () -> new Weapon("Sword", 3.5, 250, "Steel", 0, WeaponType.SWORD));
    }
}
