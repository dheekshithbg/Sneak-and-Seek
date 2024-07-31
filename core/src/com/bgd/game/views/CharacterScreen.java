package com.bgd.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bgd.game.MyGame;

public class CharacterScreen implements Screen {
    private MyGame parent;
    private Stage stage;
    private Skin skin;

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
        table.setDebug(true);
        stage.addActor(table);

        TextButton tom = new TextButton("Tom", skin);
        TextButton jerry = new TextButton("Jerry", skin);

        table.add(tom).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(jerry).fillX().uniformX();
        table.row();

        //stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

//        tom.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                Gdx.app.exit();
//            }
//        });
//
//        jerry.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                Gdx.app.exit();
//            }
//        });
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
        // Handle pause state if needed
    }

    @Override
    public void resume() {
        // Handle resume state if needed
    }

    @Override
    public void hide() {
        // Clean up resources when the screen is hidden
    }

    @Override
    public void dispose() {
        // dispose of assets when not needed anymore
        stage.dispose();
    }
}
