package ru.mrbedrockpy.bedlib.util;

import lombok.Getter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Pair<K, V> implements Map.Entry<K, V> {

    private final K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public static <K, V> Map<K, V> toMap(Collection<Pair<K, V>> pairs) {
        Map<K, V> map = new HashMap<>();
        pairs.forEach(pair -> map.put(pair.getKey(), pair.getValue()));
        return map;
    }

    @Override
    public V setValue(V value) {
        return this.value = value;
    }
}
