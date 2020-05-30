package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.gui.SeatingChart;

public class DesktopLauncher {
	//runs the game
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.x=0;
		config.y=0;
		config.title="Seating Chart";
		config.height=800;
		config.width=800;
		new LwjglApplication(new SeatingChart(), config);
	}
}