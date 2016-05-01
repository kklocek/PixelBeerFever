package com.klocek.lowrez.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.klocek.lowrez.LowRez;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 512;
        config.height = 512 + 128;
		config.title= "Pixel Beer Fever";
		new LwjglApplication(new LowRez(), config);
	}
}
