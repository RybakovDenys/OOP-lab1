package ua.lab.knight;

import ua.lab.knight.equipment.Equipment;
import ua.lab.knight.util.EquipmentLoader;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Console entry point for Laboratory Work 1, variant 7 ("Лицар").
 * 
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

        Knight knight = new Knight("Sir Edmund Ironheart");
        ammunition.forEach(knight::equip);

        printEquippedAmmunition(knight);
        printTotalCost(knight);
        printSortedByWeight(knight);
        runPriceRangeSearch(knight);
    }

    private static void printEquippedAmmunition(Knight knight) {
        System.out.println("=== Ammunition of knight " + knight.getName() + " ===");
        knight.getEquippedItems().forEach(item -> System.out.println("  " + item));
        System.out.println();
    }

    private static void printTotalCost(Knight knight) {
        System.out.printf(Locale.US, "Total ammunition cost: %.2f GBP%n", knight.calculateTotalCost());
        System.out.printf(Locale.US, "Total ammunition weight: %.2f kg%n%n", knight.calculateTotalWeight());
    }

    private static void printSortedByWeight(Knight knight) {
        System.out.println("=== Ammunition sorted by weight (ascending) ===");
        List<Equipment> sorted = knight.sortByWeightAscending();
        for (Equipment item : sorted) {
            System.out.printf(Locale.US, "  %-14s %-20s %.2f kg%n",
                    item.getCategory(), item.getName(), item.getWeight());
        }
        System.out.println();
    }

    private static void runPriceRangeSearch(Knight knight) {
        try (Scanner scanner = new Scanner(System.in, "UTF-8")) {
            System.out.println("=== Search ammunition by price range ===");
            double min = readPrice(scanner, "Minimum price: ", 0);
            double max = readPrice(scanner, "Maximum price: ", min);

            List<Equipment> found = knight.findByPriceRange(min, max);
            if (found.isEmpty()) {
                System.out.println("No ammunition items found in the given price range.");
            } else {
                System.out.println("Found ammunition items:");
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
            System.out.println("Invalid value, please try again.");
        }
    }
}
