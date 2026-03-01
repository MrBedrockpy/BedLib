package ru.mrbedrockpy.bedLib.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChestSlot {

    R1C1(0),  R1C2(1),  R1C3(2),  R1C4(3),  R1C5(4),  R1C6(5),  R1C7(6),  R1C8(7),  R1C9(8),
    R2C1(9),  R2C2(10), R2C3(11), R2C4(12), R2C5(13), R2C6(14), R2C7(15), R2C8(16), R2C9(17),
    R3C1(18), R3C2(19), R3C3(20), R3C4(21), R3C5(22), R3C6(23), R3C7(24), R3C8(25), R3C9(26),
    R4C1(27), R4C2(28), R4C3(29), R4C4(30), R4C5(31), R4C6(32), R4C7(33), R4C8(34), R4C9(35),
    R5C1(36), R5C2(37), R5C3(38), R5C4(39), R5C5(40), R5C6(41), R5C7(42), R5C8(43), R5C9(44),
    R6C1(45), R6C2(46), R6C3(47), R6C4(48), R6C5(49), R6C6(50), R6C7(51), R6C8(52), R6C9(53);

    private final int slot;

}
