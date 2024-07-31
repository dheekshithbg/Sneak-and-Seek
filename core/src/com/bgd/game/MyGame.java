package com.bgd.game;

import com.badlogic.gdx.Game;
import com.bgd.game.views.CharacterScreen;
import com.bgd.game.views.LoadingScreen;
import com.bgd.game.views.MainScreen;
import com.bgd.game.views.MenuScreen;
import com.bgd.game.views.PreferencesScreen;

public class MyGame extends Game {
	private LoadingScreen loadingScreen;
	private PreferencesScreen preferencesScreen;
	private MenuScreen menuScreen;
	private MainScreen mainScreen;
	private CharacterScreen characterScreen;

	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int APPLICATION = 2;
	public final static int CHARACTER = 3;

	@Override
	public void create() {
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
	}

	public void changeScreen(int screen) {
		switch (screen) {
			case MENU:
				if (menuScreen == null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				break;
			case PREFERENCES:
				if (preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
				this.setScreen(preferencesScreen);
				break;
			case APPLICATION:
				if (mainScreen == null) mainScreen = new MainScreen(this);
				this.setScreen(mainScreen);
				break;
			case CHARACTER:
				if (characterScreen == null) characterScreen = new CharacterScreen(this);
				this.setScreen(characterScreen);
				break;
		}
	}
}
