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

    public Structure(int width, int height, char fillChar) {
        List<String> schema = new ArrayList<>();
        for (int y = 0; y < height; y++) schema.add(String.valueOf(fillChar).repeat(Math.max(0, width)));
        this.schema = schema;
    }

    public static Structure defaultStruct(ChestSize size) {
        return new Structure(9, size.getRows(), '.');
    }

    public List<SlotData> getViewWindow(int x, int y) {
        List<SlotData> viewWindow = new ArrayList<>();
        int maxRows = Math.min(6, this.schema.size() - y);
        for (int row = 0; row < maxRows; row++) {
            int schemaRowIndex = y + row;
            if (schemaRowIndex < 0 || schemaRowIndex >= schema.size()) continue;
            String line = schema.get(schemaRowIndex);
            int maxCols = Math.min(9, line.length() - x);
            for (int col = 0; col < maxCols; col++) {
                int schemaColIndex = x + col;
                if (schemaColIndex < 0 || schemaColIndex >= line.length()) continue;
                viewWindow.add(new SlotData()
                        .structX(col).structY(row).slot(row * 9 + col)
                        .symbol(line.charAt(schemaColIndex)));
            }
        }
        return viewWindow;
    }

    public char getIndex(int x, int y) {
        return this.schema.get(y).charAt(x);
    }

    public void setIndex(int x, int y, char ch) {
        String s = this.schema.get(y);
        s = s.substring(0, x) + ch + s.substring(x + 1);
        this.schema.set(y, s);
    }

    public void addRow(char fillSymbol, boolean isUp) {
        StringBuilder row = new StringBuilder();
        for (int i = 0; i < this.getWidth(); i++) row.append(fillSymbol);
        if (isUp) this.schema.add(0, row.toString());
        else this.schema.add(row.toString());
    }

    public void addCol(char fillSymbol, boolean isLeft) {
        for (int i = 0; i < this.getHeight(); i++) {
            String row = this.schema.get(i);
            if (isLeft) row = fillSymbol + row;
            else row += fillSymbol;
            this.schema.set(i, row);
        }
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
