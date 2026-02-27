package ru.mrbedrockpy.bedLib.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChestSize {

    ROW_1(1, 9),
    ROW_2(2, 18),
    ROW_3(3, 27),
    ROW_4(4, 36),
    ROW_5(5, 45),
    ROW_6(6, 54);

    private final int rows;
    private final int size;

}
