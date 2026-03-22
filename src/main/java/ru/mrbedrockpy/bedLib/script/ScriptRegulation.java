package ru.mrbedrockpy.bedLib.script;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.mrbedrockpy.bedLib.BedPlugin;
import ru.mrbedrockpy.bedLib.text.Placeholder;
import ru.mrbedrockpy.bedLib.text.Text;

import java.util.List;
import java.util.function.BiConsumer;

@Getter
@AllArgsConstructor
public class ScriptRegulation<P extends BedPlugin<P>> {

    public static final ScriptRegulation<?> MESSAGE = new ScriptRegulation<>("msg", "", (ctx, args) -> {
        ctx.getPlayer().sendMessage(
                Text.fromText(String.join(" ", args))
                        .applyPlaceholders(Placeholder.PLAYER.apply(ctx.getPlayer()))
                        .toAdventure()
        );
    });

    public static final ScriptRegulation<?> PLAYER_CMD = new ScriptRegulation<>("msg", "", (ctx, args) -> {
        ctx.getPlayer().performCommand(
                Text.fromText(String.join(" ", args))
                        .applyPlaceholders(Placeholder.PLAYER.apply(ctx.getPlayer()))
                        .toText()
        );
    });

    public static final ScriptRegulation<?> CONSOLE_CMD = new ScriptRegulation<>("msg", "", (ctx, args) -> {
        ctx.getServer().dispatchCommand(
                ctx.getServer().getConsoleSender(),
                Text.fromText(String.join(" ", args))
                        .applyPlaceholders(Placeholder.PLAYER.apply(ctx.getPlayer()))
                        .toText()
        );
    });

    private final String id;
    private final String separator;
    private final BiConsumer<ScriptContext<P>, List<String>> consumer;

    public void exec(ScriptContext<P> context, List<String> args) {
        this.consumer.accept(context, args);
    }
}
