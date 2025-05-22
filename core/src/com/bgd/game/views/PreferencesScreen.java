package com.bgd.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bgd.game.AppPreferences;
import com.bgd.game.MyGame;

public class PreferencesScreen implements Screen {
	private MyGame parent;
	private Stage stage;
	private Skin skin;
	private AppPreferences appPreferences; // Add this line

	// UI Elements
	private Label titleLabel;
	private Label volumeMusicLabel;
	private Label volumeSoundLabel;
	private Label musicOnOffLabel;
	private Label soundOnOffLabel;
	private Slider volumeMusicSlider;
	private Slider volumeSoundSlider;
	private CheckBox musicCheckbox;
	private CheckBox soundEffectsCheckbox;
	private TextButton backButton;

	public PreferencesScreen(MyGame myGame) {
		parent = myGame;
		appPreferences = new AppPreferences(); // Initialize AppPreferences
	}

	@Override
	public void show() {
		skin = new Skin(Gdx.files.internal("glassy-ui.json"));
		titleLabel = new Label("Preferences", skin);
		volumeMusicLabel = new Label("Music Volume:", skin);
		volumeSoundLabel = new Label("Sound Volume:", skin);
		musicOnOffLabel = new Label("Music:", skin);
		soundOnOffLabel = new Label("Sound Effects:", skin);

		volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
		volumeMusicSlider.setValue(appPreferences.getMusicVolume());
		volumeMusicSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				appPreferences.setMusicVolume(volumeMusicSlider.getValue());
			}
		});

		volumeSoundSlider = new Slider(0f, 1f, 0.1f, false, skin);
		volumeSoundSlider.setValue(appPreferences.getSoundVolume());
		volumeSoundSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				appPreferences.setSoundVolume(volumeSoundSlider.getValue());
			}
		});

		musicCheckbox = new CheckBox(null, skin);
		musicCheckbox.setChecked(appPreferences.isMusicEnabled());
		musicCheckbox.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				appPreferences.setMusicEnabled(musicCheckbox.isChecked());
			}
		});

		soundEffectsCheckbox = new CheckBox(null, skin);
		soundEffectsCheckbox.setChecked(appPreferences.isSoundEffectsEnabled());
		soundEffectsCheckbox.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				appPreferences.setSoundEffectsEnabled(soundEffectsCheckbox.isChecked());
			}
		});

		backButton = new TextButton("Back", skin, "small");
		backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(MyGame.MENU);
			}
		});

		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		Table table = new Table();
		table.setFillParent(true);
		table.setDebug(true);
		stage.addActor(table);

		table.add(titleLabel).padBottom(10);
		table.row();
		table.add(volumeMusicLabel).left();
		table.add(volumeMusicSlider).fillX();
		table.row();
		table.add(musicOnOffLabel).left();
		table.add(musicCheckbox).left();
		table.row();
		table.add(volumeSoundLabel).left();
		table.add(volumeSoundSlider).fillX();
		table.row();
		table.add(soundOnOffLabel).left();
		table.add(soundEffectsCheckbox).left();
		table.row();
		table.add(backButton).colspan(2).bottom().padTop(20);
		table.setDebug(true);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}
}
