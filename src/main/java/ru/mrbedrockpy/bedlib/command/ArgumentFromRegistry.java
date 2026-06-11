package ru.mrbedrockpy.bedlib.command;

import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;
import ru.mrbedrockpy.bedLib.BedPlugin;
import ru.mrbedrockpy.bedLib.manager.Dto;
import ru.mrbedrockpy.bedLib.manager.Registry;
import ru.mrbedrockpy.bedLib.serialize.Serializers;
import ru.mrbedrockpy.bedLib.text.Placeholder;
import ru.mrbedrockpy.bedLib.text.Text;

public class ArgumentFromRegistry<P extends BedPlugin<P>, T extends Dto> extends Argument<P, T> {

    private static final Placeholder<String> ARGUMENT_VALUE_PLACEHOLDER = new Placeholder<>("arg", Serializers.STRING.getSerializer());

    private final Registry<T> registry;
    private final Text notFoundMessage;

    public ArgumentFromRegistry(P plugin, Registry<T> registry, Class<T> type) {
        this(plugin, registry, type, Text.fromText("<red>%arg% not found!"));
    }

    public ArgumentFromRegistry(P plugin, Registry<T> registry, Class<T> type, Text notFoundMessage) {
        super(plugin, type);
        this.registry = registry;
        this.notFoundMessage = notFoundMessage;
    }

    @Override
    protected ParseResult<T> parse(Invocation<CommandSender> invocation, dev.rollczi.litecommands.argument.Argument<T> context, String argument) {
        return this.registry.getAsCommandArgument(argument, this.notFoundMessage.applyPlaceholders(ARGUMENT_VALUE_PLACEHOLDER.apply(argument)));
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, dev.rollczi.litecommands.argument.Argument<T> argument, SuggestionContext context) {
        return this.registry.getKeys().stream().collect(SuggestionResult.collector());
    }
}
