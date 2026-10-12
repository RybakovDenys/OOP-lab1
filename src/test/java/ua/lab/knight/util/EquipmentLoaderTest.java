package ua.lab.knight.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ua.lab.knight.EquipmentSlot;
import ua.lab.knight.equipment.Equipment;
import ua.lab.knight.equipment.Weapon;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EquipmentLoaderTest {

    @Test
    void loadsBundledEquipmentResourceSuccessfully() {
        List<Equipment> items = EquipmentLoader.loadFromClasspath("/equipment.txt");

        assertFalse(items.isEmpty());
    }

    @Test
    void loadsAllEquipmentCategoriesFromFile(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("custom-equipment.txt");
        Files.writeString(file, String.join(System.lineSeparator(),
                "# comment line should be skipped",
                "",
                "HELMET;Helmet;2.0;150;Steel;15",
                "CHESTPLATE;Cuirass;12.0;420;Steel;40",
                "LEGGINGS;Leggings;4.0;130;Steel;20",
                "GAUNTLETS;Gauntlets;1.5;90;Steel;10",
                "BOOTS;Boots;2.0;70;Leather;8",
                "SHIELD;Shield;6.0;190;Wood;30",
                "WEAPON;Sword;3.5;260;Steel;45;SWORD"
        ), StandardCharsets.UTF_8);

        List<Equipment> items = EquipmentLoader.loadFromFile(file);

        assertEquals(7, items.size());
        Equipment weapon = items.stream()
                .filter(item -> item.getSlot() == EquipmentSlot.WEAPON)
                .findFirst()
                .orElseThrow();
        assertTrue(weapon instanceof Weapon);
        assertEquals(45, ((Weapon) weapon).getDamage());
    }

    @Test
    void throwsOnUnknownCategory(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("bad-equipment.txt");
        Files.writeString(file, "CROWN;Crown;1.0;500;Gold;5", StandardCharsets.UTF_8);

        assertThrows(IllegalArgumentException.class, () -> EquipmentLoader.loadFromFile(file));
    }

    @Test
    void throwsOnMalformedLine(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("malformed-equipment.txt");
        Files.writeString(file, "HELMET;Helmet;2.0", StandardCharsets.UTF_8);

        assertThrows(IllegalArgumentException.class, () -> EquipmentLoader.loadFromFile(file));
    }

    @Test
    void throwsWhenWeaponIsMissingWeaponType(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("weapon-missing-type.txt");
        Files.writeString(file, "WEAPON;Sword;3.5;260;Steel;45", StandardCharsets.UTF_8);

        assertThrows(IllegalArgumentException.class, () -> EquipmentLoader.loadFromFile(file));
    }

    @Test
    void throwsWhenClasspathResourceIsMissing() {
        assertThrows(IllegalArgumentException.class,
                () -> EquipmentLoader.loadFromClasspath("/does-not-exist.txt"));
    }
}
