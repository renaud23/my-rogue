package com.renaud.rogue.game.layout;

import com.renaud.rogue.game.event.ActionEvent;
import com.renaud.rogue.game.tools.Rectangle;

public interface Layout extends ActionEvent {
    Rectangle getRectangle();

    void setActif(boolean actif);

    boolean isActif();

    int getColor();
}
