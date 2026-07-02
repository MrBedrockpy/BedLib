package ru.mrbedrockpy.bedlib.text;

import lombok.RequiredArgsConstructor;
import ru.mrbedrockpy.bedlib.BedPlugin;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PlaceholderManager<P extends BedPlugin<P>> {

    private final P plugin;
    private final List<Placeholder<P>> placeholders = new ArrayList<>();

    public void placeholder(Placeholder<P> placeholder) {
        placeholders.add(placeholder);
    }

    public void registerPlaceholders() {
        placeholders.forEach(Placeholder::register);
    }
}
