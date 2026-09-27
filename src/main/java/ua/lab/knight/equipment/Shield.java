package ua.lab.knight.equipment;

import ua.lab.knight.EquipmentSlot;

/** A shield used by the knight to block incoming attacks. */
public class Shield extends Equipment {

    private final int blockChancePercent;

    public Shield(String name, double weight, double price, String material, int blockChancePercent) {
        super(name, weight, price, material);
        if (blockChancePercent <= 0 || blockChancePercent > 100) {
            throw new IllegalArgumentException("blockChancePercent must be in range (0, 100]");
        }
        this.blockChancePercent = blockChancePercent;
    }

    public int getBlockChancePercent() {
        return blockChancePercent;
    }

    @Override
    public String getCategory() {
        return "Shield";
    }

    @Override
    public EquipmentSlot getSlot() {
        return EquipmentSlot.SHIELD;
    }

    @Override
    public String describeCombatValue() {
        return "blockChance=" + blockChancePercent + "%";
    }
}
