package io.github.crucible.grimoire.forge.core;

/**
 * Use this function to save some lines when not using cache calls.
 *
 * @author juanmuscaria
 */
public final class EmptyFunction<V> extends Function<V> {
    @Override
    public boolean run(V value) {
        return false;
    }
}
