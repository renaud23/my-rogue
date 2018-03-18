package com.renaud.rogue.element;

import com.renaud.rogue.element.projectile.Projectile;

public interface Living extends Element {
    boolean isDead();

    void injured(Projectile projectile);

    void injured(Monster projectile);
}
