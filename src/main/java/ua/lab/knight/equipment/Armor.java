package ua.lab.knight.equipment;

/**
 * Base class for all body armor pieces (helmet, chestplate, leggings,
 * gauntlets, boots). Adds a defense bonus on top of the common
 * {@link Equipment} properties.
 */
public abstract class Armor extends Equipment {

    private final int defenseBonus;

    protected Armor(String name, double weight, double price, String material, int defenseBonus) {
        super(name, weight, price, material);
        if (defenseBonus <= 0) {
            throw new IllegalArgumentException("defenseBonus must be a positive number");
        }
        this.defenseBonus = defenseBonus;
    }

    public int getDefenseBonus() {
        return defenseBonus;
    }

    @Override
    public String getCategory() {
        return "Armor";
    }

    @Override
    public String describeCombatValue() {
        return "defense=+" + defenseBonus;
    }
}
