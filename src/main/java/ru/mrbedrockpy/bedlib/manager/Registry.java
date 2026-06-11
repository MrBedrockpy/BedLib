package ru.mrbedrockpy.bedlib.manager;

import dev.rollczi.litecommands.argument.parser.ParseResult;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public interface Registry<I> {

    void clear();

    List<String> getKeys();
    List<I> getElements();
    Map<String, I> getItems();

    boolean register(I item);
    boolean registerAll(Collection<I> items);
    boolean registerAll(I... items);

    boolean unregister(I item);
    boolean unregisterAll(Collection<I> items);
    boolean unregisterAll(I... items);

    @Nullable I get(String id);
    I getOrDefault(String id, I defaultValue);
    I getAndCreateIfNotExists(String id, I defaultValue);
    ParseResult<I> getAsCommandArgument(String id, Object failure);

}
