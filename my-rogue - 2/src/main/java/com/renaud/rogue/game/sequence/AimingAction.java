package com.renaud.rogue.game.sequence;

import com.renaud.rogue.game.world.Game;

public interface AimingAction {

    void aimUp();

    void aimDown();

    void aimLeft();

    void aimRight();

    int getDepht();

    void activate(Game game);

    void changeSequence(Game game);

}
