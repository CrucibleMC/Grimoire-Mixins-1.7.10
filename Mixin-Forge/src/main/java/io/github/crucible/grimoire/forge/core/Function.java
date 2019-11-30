package io.github.crucible.grimoire.forge.core;

/**
 * Abstraction for some simple cache calls.
 *
 * @author Jikoo
 */
public abstract class Function<V> {

    public abstract boolean run(V value);

}