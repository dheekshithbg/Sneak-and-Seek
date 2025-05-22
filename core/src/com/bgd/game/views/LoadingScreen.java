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
		// TODO Auto-generated method stub
	}
 
	@Override
	public void render(float delta) {
		parent.changeScreen(MyGame.MENU);
	}
 
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}
 
	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}
 
	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}
 
	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}
 
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}
}
