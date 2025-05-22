package com.bgd.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bgd.game.MyGame;

public class CharacterScreen implements Screen {
    private MyGame parent;
    private Stage stage;
    private Label titleLabel;
    public CharacterScreen(MyGame myGame) {
        parent = myGame;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Skin skin = new Skin(Gdx.files.internal("glassy-ui.json"));

        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);

        titleLabel = new Label("Select your character", skin);
        titleLabel.setFontScale(1.2f);

        TextButton tom = new TextButton("Tom", skin);
        TextButton jerry = new TextButton("Jerry", skin);
        TextButton backButton = new TextButton("Back", skin, "small");

        table.add(titleLabel).padTop(10).row();
        table.add(tom).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(jerry).fillX().uniformX();
        table.row();
        table.add(backButton).colspan(2).bottom().padTop(20);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(MyGame.MENU);
            }
        });


        tom.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.setSelectedCharacter("Tom");
                parent.changeScreen(MyGame.APPLICATION);
            }
        });

        jerry.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.setSelectedCharacter("Jerry");
                parent.changeScreen(MyGame.APPLICATION);
            }
        });




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
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        stage.dispose();
    }
}
