package ru.mrbedrockpy.bedLib.command;

import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import ru.mrbedrockpy.bedLib.BedPlugin;

@Getter
@RequiredArgsConstructor
public abstract class Argument<P extends BedPlugin<P>, TYPE> extends ArgumentResolver<CommandSender, TYPE> {

    protected final P plugin;
    protected final Class<TYPE> type;

}
