package com.renaud.rogue.layout.loot;

import com.renaud.rogue.game.event.ActionEvent;
import com.renaud.rogue.game.inventaire.Item;

public interface ItemInventory extends ActionEvent {
    Item getItem();
}
