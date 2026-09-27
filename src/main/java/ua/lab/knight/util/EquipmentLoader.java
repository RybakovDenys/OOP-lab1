package ua.lab.knight.util;

import ua.lab.knight.equipment.Armor;
import ua.lab.knight.equipment.Boots;
import ua.lab.knight.equipment.Chestplate;
import ua.lab.knight.equipment.Equipment;
import ua.lab.knight.equipment.Gauntlets;
import ua.lab.knight.equipment.Helmet;
import ua.lab.knight.equipment.Leggings;
import ua.lab.knight.equipment.Shield;
import ua.lab.knight.equipment.Weapon;
import ua.lab.knight.equipment.WeaponType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads {@link Equipment} initialization parameters from a semicolon
 * separated text file (see {@code src/main/resources/equipment.txt} for
 * the exact format), so ammunition data does not have to be hard-coded.
 */
public final class EquipmentLoader {

    private static final String FIELD_SEPARATOR = ";";
    private static final String COMMENT_PREFIX = "#";

    private EquipmentLoader() {
        // Utility class - not meant to be instantiated.
    }

    /**
     * Loads equipment definitions from a file on the file system.
     */
    public static List<Equipment> loadFromFile(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            return parse(reader);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read equipment file: " + path, e);
        }
    }

    /**
     * Loads equipment definitions from a classpath resource, e.g.
     * {@code "/equipment.txt"}.
     */
    public static List<Equipment> loadFromClasspath(String resourceName) {
        try (InputStream inputStream = EquipmentLoader.class.getResourceAsStream(resourceName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: " + resourceName);
            }
            try (BufferedReader reader =
                         new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                return parse(reader);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read equipment resource: " + resourceName, e);
        }
    }

    private static List<Equipment> parse(BufferedReader reader) throws IOException {
        List<Equipment> items = new ArrayList<>();
        String line;
        int lineNumber = 0;
        while ((line = reader.readLine()) != null) {
            lineNumber++;
            String trimmed = line.trim();
            if (trimmed.isEmpty() || trimmed.startsWith(COMMENT_PREFIX)) {
                continue;
            }
            items.add(parseLine(trimmed, lineNumber));
        }
        return items;
    }

    private static Equipment parseLine(String line, int lineNumber) {
        String[] fields = line.split(FIELD_SEPARATOR);
        if (fields.length < 6) {
            throw new IllegalArgumentException(
                    "Malformed equipment definition on line " + lineNumber + ": " + line);
        }
        String category = fields[0].trim().toUpperCase();
        String name = fields[1].trim();
        double weight = Double.parseDouble(fields[2].trim());
        double price = Double.parseDouble(fields[3].trim());
        String material = fields[4].trim();

        switch (category) {
            case "HELMET":
                return new Helmet(name, weight, price, material, parseInt(fields[5]));
            case "CHESTPLATE":
                return new Chestplate(name, weight, price, material, parseInt(fields[5]));
            case "LEGGINGS":
                return new Leggings(name, weight, price, material, parseInt(fields[5]));
            case "GAUNTLETS":
                return new Gauntlets(name, weight, price, material, parseInt(fields[5]));
            case "BOOTS":
                return new Boots(name, weight, price, material, parseInt(fields[5]));
            case "SHIELD":
                return new Shield(name, weight, price, material, parseInt(fields[5]));
            case "WEAPON":
                if (fields.length < 7) {
                    throw new IllegalArgumentException(
                            "Weapon definition on line " + lineNumber + " is missing weaponType: " + line);
                }
                int damage = parseInt(fields[5]);
                WeaponType weaponType = WeaponType.valueOf(fields[6].trim().toUpperCase());
                return new Weapon(name, weight, price, material, damage, weaponType);
            default:
                throw new IllegalArgumentException(
                        "Unknown equipment category '" + category + "' on line " + lineNumber);
        }
    }

    private static int parseInt(String value) {
        return Integer.parseInt(value.trim());
    }

    /**
     * Convenience helper that reports whether a loaded item is armor.
     * Kept here to demonstrate simple polymorphic filtering without
     * duplicating instanceof checks across the codebase.
     */
    public static boolean isArmor(Equipment equipment) {
        return equipment instanceof Armor;
    }
}
