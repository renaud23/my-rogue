package com.renaud.rogue.game.layout;

public interface GridLayoutListener<U> {
    default void activate(U u, int i, int j) {
    };

    default void over(U u, int i, int j) {
    };
}
