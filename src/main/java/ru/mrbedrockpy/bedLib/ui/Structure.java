package ru.mrbedrockpy.bedLib.ui;

import java.util.*;

public class Structure {

    private final List<String> schema;

    public Structure(List<String> schema) {
        this.schema = this.validate(schema);
    }
    public Structure(String... schema) {
        this(Arrays.asList(schema));
    }

    public Map<Integer, Character> getIngredients(int x, int y) {
        Map<Integer, Character> ingredients = new HashMap<>();
        int maxRows = Math.min(6, this.schema.size() - y);
        for (int row = 0; row < maxRows; row++) {
            int schemaRowIndex = y + row;
            if (schemaRowIndex < 0 || schemaRowIndex >= schema.size()) continue;
            String line = schema.get(schemaRowIndex);
            int maxCols = Math.min(9, line.length() - x);
            for (int col = 0; col < maxCols; col++) {
                int schemaColIndex = x + col;
                if (schemaColIndex < 0 || schemaColIndex >= line.length()) continue;
                char ch = line.charAt(schemaColIndex);
                int slot = row * 9 + col;
                ingredients.put(slot, ch);
            }
        }
        return ingredients;
    }

    public int getWidth() {
        return this.schema.stream().mapToInt(String::length).max().orElse(0);
    }
    public int getHeight() {
        return this.schema.size();
    }
    private List<String> validate(List<String> input) {
        return input.stream().map(s -> s.replace(" ", "")).toList();
    }

}
