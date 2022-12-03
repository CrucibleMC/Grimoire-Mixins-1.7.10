package io.github.crucible.grimoire.mixins.funkylocomotion;

import com.rwtema.funkylocomotion.movepermissions.MoveCheckReflector;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;

@Mixin(value = MoveCheckReflector.class, remap = false)
public abstract class MixinMoveCheckReflector {

    @Shadow
    @Final
    private static HashMap<Class<?>, Boolean> cache;

    /**
     * @author Rehab_CZ
     * @reason Add NoClassDefFoundError catch
     */
    @Overwrite
    private static boolean _canMoveClass(Class<?> clazz) {
        try {
            Method method = clazz.getMethod("_Immovable");
            if (Modifier.isStatic(method.getModifiers()) &&
                    Modifier.isPublic(method.getModifiers()))
                if (method.getReturnType() == boolean.class) {
                    Boolean b = (Boolean) method.invoke(null);
                    return b == null || !b;
                }
            return true;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | RuntimeException |
                 NoClassDefFoundError e) {
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * @author Rehab_CZ [Taken from MagiHandlers]
     * @reason Check for empty before move
     */
    @Overwrite
    public static boolean canMoveClass(Class<?> clazz) {
        if (clazz == null) return true;

        Boolean b = (Boolean) cache.get(clazz);
        if (b == null) {
            boolean bool = false;
            try {
                bool = _canMoveClass(clazz);
            } catch (Throwable e) {
            }
            b = Boolean.valueOf(bool);
            cache.put(clazz, b);
        }
        return b.booleanValue();
    }

}
