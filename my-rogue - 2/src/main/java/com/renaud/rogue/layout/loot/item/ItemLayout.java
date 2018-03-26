package com.renaud.rogue.layout.loot.item;

import com.renaud.rogue.game.event.ActionEvent;
import com.renaud.rogue.game.inventaire.Item;

public interface ItemLayout extends ActionEvent {
    Item getItem();
}
