package ru.mrbedrockpy.bedlib.ui.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class UpdateItem implements Item {

    private final int interval;

}
