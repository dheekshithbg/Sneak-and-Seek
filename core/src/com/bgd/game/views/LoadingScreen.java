package com.bgd.game.views;

import com.badlogic.gdx.Screen;
import com.bgd.game.MyGame;

public class LoadingScreen implements Screen {
    private MyGame parent;

    public LoadingScreen(MyGame myGame){
        parent=myGame;
    }
    
    @Override
	public void show() {

	}
 
	@Override
	public void render(float delta) {
		parent.changeScreen(MyGame.MENU);
	}
 
	@Override
	public void resize(int width, int height) {

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

	}
}
