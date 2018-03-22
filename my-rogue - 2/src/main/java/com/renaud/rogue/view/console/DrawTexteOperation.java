package com.renaud.rogue.view.console;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

public interface DrawTexteOperation {
    Rectangle getBound(String str, Font font, int x, int y);

    void drawString(String str, int x, int y, Font font, Color color);
}
