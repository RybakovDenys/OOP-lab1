package ua.lab.knight;

import ua.lab.knight.equipment.Equipment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A knight that can be equipped with ammunition (equipment). Each equipment
 * slot ({@link EquipmentSlot}) can hold at most one item at a time, so
 * equipping a new item into an occupied slot replaces the previous one.
 */
public class Knight {

    private final String name;
    private final Map<EquipmentSlot, Equipment> equippedItems = new EnumMap<>(EquipmentSlot.class);

    public Knight(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Knight name must not be null or blank");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Equips the given item into its designated slot, replacing whatever
     * was equipped there before.
     *
     * @param item the equipment item to equip; must not be {@code null}.
     * @return the previously equipped item in that slot, if any.
     */
    public Optional<Equipment> equip(Equipment item) {
        if (item == null) {
            throw new IllegalArgumentException("item must not be null");
        }
        Equipment previous = equippedItems.put(item.getSlot(), item);
        return Optional.ofNullable(previous);
    }

    /**
     * Removes and returns whatever is equipped in the given slot.
     */
    public Optional<Equipment> unequip(EquipmentSlot slot) {
        return Optional.ofNullable(equippedItems.remove(slot));
    }

    public Optional<Equipment> getEquippedItem(EquipmentSlot slot) {
        return Optional.ofNullable(equippedItems.get(slot));
    }

    /**
     * @return an immutable-ish snapshot list of all currently equipped items.
     */
    public List<Equipment> getEquippedItems() {
        return new ArrayList<>(equippedItems.values());
    }

    /**
     * @return the total cost of all equipped ammunition.
     */
    public double calculateTotalCost() {
        return equippedItems.values().stream()
                .mapToDouble(Equipment::getPrice)
                .sum();
    }

    /**
     * @return the total weight (kg) of all equipped ammunition.
     */
    public double calculateTotalWeight() {
        return equippedItems.values().stream()
                .mapToDouble(Equipment::getWeight)
                .sum();
    }

    /**
     * @return the equipped ammunition sorted by weight, ascending.
     */
    public List<Equipment> sortByWeightAscending() {
        List<Equipment> sorted = getEquippedItems();
        sorted.sort(Comparator.naturalOrder());
        return sorted;
    }

    /**
     * @return the equipped ammunition sorted by weight, descending.
     */
    public List<Equipment> sortByWeightDescending() {
        List<Equipment> sorted = getEquippedItems();
        sorted.sort(Comparator.comparingDouble(Equipment::getWeight).reversed());
        return sorted;
    }

    /**
     * Finds every equipped item whose price falls within the given
     * inclusive range.
     *
     * @param minPrice lower bound of the price range (inclusive).
     * @param maxPrice upper bound of the price range (inclusive).
     * @return a list of matching equipment items; empty if none match.
     */
    public List<Equipment> findByPriceRange(double minPrice, double maxPrice) {
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("minPrice must not be greater than maxPrice");
        }
        List<Equipment> result = new ArrayList<>();
        for (Equipment item : equippedItems.values()) {
            if (item.getPrice() >= minPrice && item.getPrice() <= maxPrice) {
                result.add(item);
            }
        }
        result.sort(Comparator.comparingDouble(Equipment::getPrice));
        return result;
    }

    @Override
    public String toString() {
        return "Knight{name='" + name + "', equippedItems=" + equippedItems.size() + "}";
    }
}
