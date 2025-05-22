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

	private String selectedCharacter;

	@Override
	public void create() {
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
	}

	public String getSelectedCharacter() {
		return selectedCharacter;
	}

	public void setSelectedCharacter(String character) {
		this.selectedCharacter = character;
	}

	public void changeScreen(int screen) {
		switch (screen) {
			case MENU:
				if (menuScreen == null) menuScreen = new MenuScreen(this);
				setScreen(menuScreen);
				break;
			case PREFERENCES:
				if (preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
				setScreen(preferencesScreen);
			 	break;
			case APPLICATION:
				if (selectedCharacter == null) {
					System.out.println("No character selected. Redirecting to character screen...");
					changeScreen(CHARACTER);
					return;
				}
				mainScreen = new MainScreen(this, selectedCharacter);
				setScreen(mainScreen);
				break;

			case CHARACTER:
				if (characterScreen == null) characterScreen = new CharacterScreen(this);
				setScreen(characterScreen);
				break;
		}
	}
}
