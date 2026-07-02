package ru.mrbedrockpy.bedlib.autoregister;

import dev.rollczi.litecommands.annotations.command.Command;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import ru.mrbedrockpy.bedlib.BedPlugin;
import ru.mrbedrockpy.bedlib.command.Argument;
import ru.mrbedrockpy.bedlib.text.Placeholder;

public class AutoRegisterManager {

    public static <P extends BedPlugin<P>> void register(P plugin) {
        try (ScanResult scanResult = new ClassGraph()
                .enableClassInfo().enableAnnotationInfo()
                .addClassLoader(plugin.getClass().getClassLoader())
                .scan()) {
            for (ClassInfo classInfo : scanResult.getClassesWithAnnotation(AutoRegister.class.getName())) {
                Class<?> clazz = classInfo.loadClass();
                Object instance = getInstance(plugin, clazz);
                if (clazz.getAnnotation(Command.class) != null)
                    plugin.getCommandManager().command(instance);
                if (instance instanceof Argument<?, ?> argument)
                    plugin.getCommandManager().argument((Argument<P, ?>) argument);
                if (instance instanceof Placeholder<?> placeholder)
                    plugin.getPlaceholderManager().placeholder((Placeholder<P>) placeholder);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <P extends BedPlugin<P>> Object getInstance(P plugin, Class<?> clazz) {
        try {
            return clazz.getConstructor(plugin.getClass()).newInstance(plugin);
        } catch (ReflectiveOperationException e) {
            try {
                return clazz.getConstructor().newInstance();
            } catch (ReflectiveOperationException e1) {
                throw new RuntimeException("Can't find constructor for " + clazz.getName());
            }
        }
    }
}
