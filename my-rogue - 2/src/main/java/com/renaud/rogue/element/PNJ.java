package com.renaud.rogue.element;

public interface PNJ extends Element, TurnPlay {

    void addX(int dx);

    void addY(int dy);

    boolean isDead();

    int getDepht();

}
