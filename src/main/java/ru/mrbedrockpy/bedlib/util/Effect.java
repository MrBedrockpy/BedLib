package ru.mrbedrockpy.bedlib.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Getter
@Setter
@AllArgsConstructor
public class Effect {

    private final PotionEffectType type;
    private int duration;
    private int amplifier;

    public static Effect of(PotionEffectType type, int duration, int amplifier) {
        return new Effect(type, duration, amplifier);
    }

    public PotionEffect toMinecraft() {
        return new PotionEffect(type, duration, amplifier);
    }
}
