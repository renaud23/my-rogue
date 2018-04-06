package com.renaud.rogue.view.console;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

import com.renaud.rogue.view.drawer.GameConsoleDrawer;

public class ConsoleStream extends OutputStream {

	private StringBuilder buffer = new StringBuilder();

	@Override
	public void write(int b) throws IOException {
		char c = (char) b;
		if (c != '\n') {
			buffer.append(c);
		} else {
			if (GameConsoleDrawer.getConsole() != null) {
				GameConsoleDrawer.getConsole().addLigne(buffer.toString(), Color.magenta);
			}
			buffer = new StringBuilder();
		}

	}

}
