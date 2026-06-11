package ru.mrbedrockpy.bedlib.script;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.mrbedrockpy.bedlib.BedPlugin;

import java.util.*;

@Getter
@AllArgsConstructor
public class Script<P extends BedPlugin<P>> {

    private final List<String> args;
    private final List<ScriptRegulation<P>> regulations;

    public static <P extends BedPlugin<P>> Script<P> of(P plugin, List<String> args) {
        return new Script<>(args, new ArrayList<>());
    }

    public Script<P> regulation(ScriptRegulation<P> regulation) {
        this.regulations.add(regulation);
        return this;
    }

    public void exec(ScriptContext<P> context) {
        Map<String, ScriptRegulation<P>> regulationMap = new HashMap<>();
        this.args.forEach(arg -> {
            for (ScriptRegulation<P> regulation : this.regulations) {
                String regulationHeader = "[" + regulation.getId() + "] ";
                if (arg.startsWith(regulationHeader)) {
                    regulationMap.put(arg.substring(regulationHeader.length()), regulation);
                    return;
                }
            }
            context.getPlugin().getLogger().warning("Regulation for " + arg + "not found!");
        });
        regulationMap.forEach((s, regulation) -> {
            regulation.exec(context, Arrays.asList(s.split(regulation.getSeparator())));
        });
    }
}
