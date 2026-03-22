package ru.mrbedrockpy.bedLib.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.enchantments.Enchantment;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class Enchant {

    private Enchantment enchantment;
    private int level;

    public Enchant(Map.Entry<Enchantment, Integer> enchant) {
        this.enchantment = enchant.getKey();
        this.level = enchant.getValue();
    }

    public static Enchant of(Enchantment enchantment, int level) {
        return new Enchant(enchantment, level);
    }
    public static Enchant of(Map.Entry<Enchantment, Integer> enchant) {
        return new Enchant(enchant);
    }

}
