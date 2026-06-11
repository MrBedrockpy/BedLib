package ru.mrbedrockpy.bedlib.ui.gui;

import ru.mrbedrockpy.bedlib.text.Text;
import ru.mrbedrockpy.bedlib.ui.ChestSize;
import ru.mrbedrockpy.bedlib.ui.SlotData;
import java.util.Map;

public interface Gui {

    Text getTitle();

    ChestSize getSize();

    Map<Integer, SlotData> render();

    static SlotGui slot(ChestSize size) {
        return new SlotGui(size);
    }
    static StructGui struct(ChestSize size) {
        return new StructGui(size);
    }
    static PagedGui paged(ChestSize size) {
        return new PagedGui(size);
    }
    static OverviewGui overview(ChestSize size) {
        return new OverviewGui(size);
    }
}
