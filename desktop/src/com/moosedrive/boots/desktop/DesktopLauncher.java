package com.moosedrive.boots.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.moosedrive.boots.BootsGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.title = "Boots Item Shop";
                config.width = 1280;
                config.height = 720;
		new LwjglApplication(new BootsGame(), config);
	}
}
