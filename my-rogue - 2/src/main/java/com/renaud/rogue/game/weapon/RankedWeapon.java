package com.renaud.rogue.game.weapon;

import com.renaud.rogue.game.inventaire.Ammunition;

public interface RankedWeapon extends Weapon {

    void reload(Ammunition ammo);

    boolean isEmpty();

    int getRemainingAmmos();
}
