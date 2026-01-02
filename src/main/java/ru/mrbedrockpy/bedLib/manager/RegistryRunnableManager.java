package ru.mrbedrockpy.bedLib.manager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mrbedrockpy.bedLib.BedPlugin;

import java.util.*;

public abstract class RegistryRunnableManager<P extends BedPlugin<P>, I extends ManagerItem> extends RunnableManager<P> {

    private final Map<String, I> items = new HashMap<>();
    private final DuplicatePolicy duplicatePolicy;

    public RegistryRunnableManager(P plugin) {
        super(plugin);
        this.duplicatePolicy = DuplicatePolicy.OVERWRITE;
    }

    public RegistryRunnableManager(P plugin, DuplicatePolicy duplicatePolicy) {
        super(plugin);
        this.duplicatePolicy = duplicatePolicy;
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

    @Nullable public I get(String id) {
        return this.items.get(id);
    }
    @NotNull public I getOrDefault(String id, I defaultValue) {
        return this.items.getOrDefault(id, defaultValue);
    }
    @NotNull public I getAndCreateIfNotExists(String id, I defaultValue) {
        I item = this.items.get(id);
        if (item == null) {
            item = defaultValue;
            register(item);
        }
        return item;
    }
}
