package ru.mrbedrockpy.bedlib.sidebar;

import lombok.Getter;
import ru.mrbedrockpy.bedlib.text.Text;

@Getter
public class SidebarLine {

    private final Text text;
    private final boolean empty;

    private SidebarLine(Text text, boolean empty) {
        this.text = text;
        this.empty = empty;
    }

    public static SidebarLine text(String text) {
        return text(Text.fromText(text));
    }
    public static SidebarLine text(Text text) {
        return new SidebarLine(text, false);
    }

    public static SidebarLine empty() {
        return new SidebarLine(Text.empty(), true);
    }

}