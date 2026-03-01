package ru.mrbedrockpy.bedLib.serialize;

public class Serializers {

    public static final Serializer<Byte> BYTE = new Serializer<>(Byte.class, String::valueOf, Byte::parseByte);
    public static final Serializer<Short> SHORT = new Serializer<>(Short.class, String::valueOf, Short::parseShort);
    public static final Serializer<Integer> INTEGER = new Serializer<>(Integer.class, String::valueOf, Integer::parseInt);
    public static final Serializer<Long> LONG = new Serializer<>(Long.class, String::valueOf, Long::parseLong);

    public static final Serializer<Float> FLOAT = new Serializer<>(Float.class, String::valueOf, Float::parseFloat);
    public static final Serializer<Double> DOUBLE = new Serializer<>(Double.class, String::valueOf, Double::parseDouble);

    public static final Serializer<Boolean> BOOLEAN = new Serializer<>(Boolean.class, String::valueOf, Boolean::parseBoolean);
    public static final Serializer<Character> CHAR = new Serializer<>(Character.class, String::valueOf, s -> s.charAt(0));
    public static final Serializer<String> STRING = new Serializer<>(String.class, s -> s, s -> s);

}
