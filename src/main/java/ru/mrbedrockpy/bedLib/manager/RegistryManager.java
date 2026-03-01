package ru.mrbedrockpy.bedLib.manager;

import dev.rollczi.litecommands.argument.parser.ParseResult;
import org.jetbrains.annotations.Nullable;
import ru.mrbedrockpy.bedLib.BedPlugin;

import java.util.*;

public abstract class RegistryManager<P extends BedPlugin<P>, I extends Dto> extends Manager<P> implements Registry<I> {

    private final Map<String, I> items = new HashMap<>();
    private final DuplicatePolicy duplicatePolicy;
    private final DtoLoader<I> loader;

    public RegistryManager(P plugin) {
        this(plugin, DuplicatePolicy.OVERWRITE);
    }

    public RegistryManager(P plugin, DuplicatePolicy duplicatePolicy) {
        this(plugin, duplicatePolicy, null);
    }

    public RegistryManager(P plugin, DtoLoader<I> loader) {
        this(plugin, DuplicatePolicy.OVERWRITE, loader);
    }

    public RegistryManager(P plugin, DuplicatePolicy duplicatePolicy, DtoLoader<I> loader) {
        super(plugin);
        this.duplicatePolicy = duplicatePolicy;
        this.loader = loader;
    }

    public void clear() {
        this.items.clear();
    }

    public List<String> getKeys() {
        return new ArrayList<>(items.keySet());
    }
    public List<I> getElements() {
        return new ArrayList<>(items.values());
    }
    public Map<String, I> getItems() {
        return new HashMap<>(items);
    }

    public boolean register(I item) {
        if (this.items.containsKey(item.getId())) {
            if (this.duplicatePolicy == DuplicatePolicy.SKIP) return false;
        }
        this.items.put(item.getId(), item);
        return true;
    }
    public boolean registerAll(Collection<I> items) {
        return items.stream().allMatch(this::register);
    }
    @SafeVarargs public final boolean registerAll(I... items) {
        return registerAll(Arrays.asList(items));
    }

    public boolean unregister(I item) {
        if (this.items.containsKey(item.getId())) {
            this.items.remove(item.getId());
            return true;
        }
        return false;
    }
    public boolean unregisterAll(Collection<I> items) {
        return items.stream().allMatch(this::unregister);
    }
    @SafeVarargs public final boolean unregisterAll(I... items) {
        return unregisterAll(Arrays.asList(items));
    }

    @Nullable
    public I get(String id) {
        return this.items.get(id);
    }

    public I getOrDefault(String id, I defaultValue) {
        return this.items.getOrDefault(id, defaultValue);
    }

    public I getAndCreateIfNotExists(String id, I defaultValue) {
        return this.items.computeIfAbsent(id, k -> defaultValue);
    }

    public ParseResult<I> getAsCommandArgument(String id, Object failure) {
        I item = this.get(id);
        if (item == null) return ParseResult.failure(failure);
        return ParseResult.success(item);
    }

    @Override
    public void load() {
        if (this.loader != null) this.loader.load();
    }

    @Override
    public void save() {
        if (this.loader != null) this.loader.save();
    }
}
