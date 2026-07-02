package ru.mrbedrockpy.bedlib.command;

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
import ru.mrbedrockpy.bedlib.BedPlugin;
import ru.mrbedrockpy.bedlib.autoregister.AutoRegister;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CommandManager<P extends BedPlugin<P>> {

    private final P plugin;
    private LiteCommands<CommandSender> liteCommands;

    private final List<Object> commands = new ArrayList<>();
    private final List<Argument<P, ?>> arguments = new ArrayList<>();

    public void command(Object command) {
        commands.add(command);
    }
    public void argument(Argument<P, ?> argument) {
        arguments.add(argument);
    }

    public void registerCommands() {
        if (this.liteCommands != null) return;
        LiteCommandsBuilder<CommandSender, LiteBukkitSettings, ?> builder = LiteBukkitFactory.builder(plugin);
        builder.commands(commands.toArray(new Object[0]));
        arguments.forEach(argument -> registerArgument(builder, argument));
        this.liteCommands = builder.build();
    }

    public void unregisterCommands() {
        if (this.liteCommands == null) return;
        this.liteCommands.unregister();
        this.liteCommands = null;
    }

    private  <T> void registerArgument(LiteCommandsBuilder<CommandSender, LiteBukkitSettings, ?> builder, Argument<P, T> argument) {
        builder.argument(argument.getType(), argument);
    }
}
