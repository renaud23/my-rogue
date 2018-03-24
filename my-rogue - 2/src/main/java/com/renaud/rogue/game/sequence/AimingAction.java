package com.renaud.rogue.game.sequence;

public interface AimingAction {

    void aimUp();

    void aimDown();

    void aimLeft();

    void aimRight();

    int getDepht();

    void activate(Game game);

}
