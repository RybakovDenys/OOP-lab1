package ua.lab.knight;

import ua.lab.knight.equipment.Equipment;
import ua.lab.knight.util.EquipmentLoader;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Console entry point for Laboratory Work 1, variant 7 ("Лицар").
 * <p>
 * Loads ammunition initialization parameters from a file, equips a knight,
 * prints the total cost, sorts the ammunition by weight and searches for
 * items that fall within a user-provided price range.
 */
public final class Main {

    private static final String EQUIPMENT_RESOURCE = "/equipment.txt";

    private Main() {
    }

    public static void main(String[] args) {
        List<Equipment> ammunition = EquipmentLoader.loadFromClasspath(EQUIPMENT_RESOURCE);

        Knight knight = new Knight("Сер Едмунд Айронхарт");
        ammunition.forEach(knight::equip);

        printEquippedAmmunition(knight);
        printTotalCost(knight);
        printSortedByWeight(knight);
        runPriceRangeSearch(knight);
    }

    private static void printEquippedAmmunition(Knight knight) {
        System.out.println("=== Амуніція лицаря " + knight.getName() + " ===");
        knight.getEquippedItems().forEach(item -> System.out.println("  " + item));
        System.out.println();
    }

    private static void printTotalCost(Knight knight) {
        System.out.printf(Locale.US, "Загальна вартість амуніції: %.2f грн%n", knight.calculateTotalCost());
        System.out.printf(Locale.US, "Загальна вага амуніції: %.2f кг%n%n", knight.calculateTotalWeight());
    }

    private static void printSortedByWeight(Knight knight) {
        System.out.println("=== Амуніція, відсортована за вагою (зростання) ===");
        List<Equipment> sorted = knight.sortByWeightAscending();
        for (Equipment item : sorted) {
            System.out.printf(Locale.US, "  %-14s %-20s %.2f кг%n",
                    item.getCategory(), item.getName(), item.getWeight());
        }
        System.out.println();
    }

    private static void runPriceRangeSearch(Knight knight) {
        try (Scanner scanner = new Scanner(System.in, "UTF-8")) {
            System.out.println("=== Пошук амуніції за діапазоном ціни ===");
            double min = readPrice(scanner, "Мінімальна ціна: ", 0);
            double max = readPrice(scanner, "Максимальна ціна: ", min);

            List<Equipment> found = knight.findByPriceRange(min, max);
            if (found.isEmpty()) {
                System.out.println("Елементів амуніції в заданому діапазоні ціни не знайдено.");
            } else {
                System.out.println("Знайдено елементи амуніції:");
                found.forEach(item -> System.out.println("  " + item));
            }
        }
    }

    private static double readPrice(Scanner scanner, String prompt, double minAllowed) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
            try {
                double value = Double.parseDouble(line);
                if (value >= minAllowed) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // fall through to error message below
            }
            System.out.println("Некоректне значення, спробуйте ще раз.");
        }
    }
}
