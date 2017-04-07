package com.castlewars.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.castlewars.game.CastleWars;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = CastleWars.WIDTH;
		config.height = CastleWars.HEIGHT;
		config.title = CastleWars.TITLE;
		new LwjglApplication(new CastleWars(), config);
	}
}
