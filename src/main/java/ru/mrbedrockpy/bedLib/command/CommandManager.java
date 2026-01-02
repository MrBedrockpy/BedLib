package ru.mrbedrockpy.bedLib.command;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.LiteCommandsBuilder;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteBukkitSettings;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import ru.mrbedrockpy.bedLib.BedPlugin;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CommandManager<P extends BedPlugin<P>> {

    private final P plugin;
    private LiteCommands<CommandSender> commands;

    public void registerCommands() {
        if (this.commands != null) return;
        LiteCommandsBuilder<CommandSender, LiteBukkitSettings, ?> builder = LiteBukkitFactory.builder(plugin);
        List<Object> commands = new ArrayList<>();
        List<Argument<P, ?>> arguments = new ArrayList<>();
        try (ScanResult scanResult = new ClassGraph()
                .enableClassInfo().enableAnnotationInfo()
                .addClassLoader(plugin.getClass().getClassLoader())
                .scan()) {
            for (ClassInfo classInfo : scanResult.getClassesWithAnnotation(AutoRegister.class.getName())) {
                Class<?> clazz = classInfo.loadClass();
                Object instance = clazz.getDeclaredConstructor(plugin.getClass()).newInstance(plugin);
                if (clazz.getAnnotation(Command.class) != null) commands.add(instance);
                if (instance instanceof Argument<?, ?> argument) arguments.add((Argument<P, ?>) argument);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        builder.commands(commands.toArray(new Object[0]));
        arguments.forEach(argument -> registerArgument(builder, argument));
        this.commands = builder.build();
    }

    public <T> void registerArgument(LiteCommandsBuilder<CommandSender, LiteBukkitSettings, ?> builder, Argument<P, T> argument) {
        builder.argument(argument.getType(), argument);
    }

    public void unregisterCommands() {
        if (this.commands == null) return;
        this.commands.unregister();
        this.commands = null;
    }
}
