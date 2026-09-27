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
                "HELMET;Шолом;2.0;150;Сталь;15",
                "CHESTPLATE;Кіраса;12.0;420;Сталь;40",
                "LEGGINGS;Набедренники;4.0;130;Сталь;20",
                "GAUNTLETS;Рукавиці;1.5;90;Сталь;10",
                "BOOTS;Чоботи;2.0;70;Шкіра;8",
                "SHIELD;Щит;6.0;190;Дерево;30",
                "WEAPON;Меч;3.5;260;Сталь;45;SWORD"
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
        Files.writeString(file, "CROWN;Корона;1.0;500;Золото;5", StandardCharsets.UTF_8);

        assertThrows(IllegalArgumentException.class, () -> EquipmentLoader.loadFromFile(file));
    }

    @Test
    void throwsOnMalformedLine(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("malformed-equipment.txt");
        Files.writeString(file, "HELMET;Шолом;2.0", StandardCharsets.UTF_8);

        assertThrows(IllegalArgumentException.class, () -> EquipmentLoader.loadFromFile(file));
    }

    @Test
    void throwsWhenWeaponIsMissingWeaponType(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("weapon-missing-type.txt");
        Files.writeString(file, "WEAPON;Меч;3.5;260;Сталь;45", StandardCharsets.UTF_8);

        assertThrows(IllegalArgumentException.class, () -> EquipmentLoader.loadFromFile(file));
    }

    @Test
    void throwsWhenClasspathResourceIsMissing() {
        assertThrows(IllegalArgumentException.class,
                () -> EquipmentLoader.loadFromClasspath("/does-not-exist.txt"));
    }
}
