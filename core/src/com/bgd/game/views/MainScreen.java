package com.bgd.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bgd.game.MyGame;

import java.util.HashSet;
import java.util.Set;

public class MainScreen implements Screen {
	private MyGame parent;
	private Stage stage;
	private String character;
	private int gridSize;
	private Image[][] grid;
	private Texture tomTexture, jerryTexture, cheeseTexture;
	private Texture lightTexture, darkTexture;

	private int tomRow = 0, tomCol = 0;
	private int jerryRow, jerryCol;
	private Set<String> cheesePositions = new HashSet<>();

	private Skin skin;
	private boolean gameOver = false;

	public MainScreen(MyGame parent, String selectedCharacter) {
		this.parent = parent;
		this.character = selectedCharacter;
		this.gridSize = 8;
		stage = new Stage(new ScreenViewport());

		tomTexture = new Texture(Gdx.files.internal("tom.png"));
		jerryTexture = new Texture(Gdx.files.internal("jerry.png"));
		cheeseTexture = new Texture(Gdx.files.internal("cheese.png"));

		skin = new Skin(Gdx.files.internal("glassy-ui.json"));

		createGrid();
		createBackButton();
	}

	private void createBackButton() {
		TextButton backButton = new TextButton("Back", skin);
		backButton.setPosition(10, Gdx.graphics.getHeight() - 100);
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				showExitConfirmation();
			}
		});
		stage.addActor(backButton);
	}

	private void showExitConfirmation() {
		Dialog dialog = new Dialog("Exit Game?", skin) {
			protected void result(Object obj) {
				boolean exit = (Boolean) obj;
				if (exit) {
					parent.setScreen(new MenuScreen(parent));
					dispose();
				}
			}
		};
		dialog.text("Are you sure you want to quit?").setColor(Color.CORAL);
		dialog.button("Yes", true).setPosition(10, 10);
		dialog.button("No", false);
		dialog.show(stage);
	}

	private void showGameOverDialog(String winner) {
		gameOver = true;
		Dialog dialog = new Dialog("Game Over", skin) {
			protected void result(Object obj) {
				parent.setScreen(new MenuScreen(parent));
				dispose();
			}
		};
		dialog.text(winner + " wins the game!").setColor(Color.BLUE);
		dialog.button("OK");
		dialog.show(stage);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	private void createGrid() {
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		Pixmap lightCell = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
		lightCell.setColor(1, 1, 1, 0.1f);
		lightCell.fill();
		lightTexture = new Texture(lightCell);
		lightCell.dispose();

		Pixmap darkCell = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
		darkCell.setColor(0, 0, 0, 0.2f);
		darkCell.fill();
		darkTexture = new Texture(darkCell);
		darkCell.dispose();

		grid = new Image[gridSize][gridSize];

		for (int row = 0; row < gridSize; row++) {
			for (int col = 0; col < gridSize; col++) {
				Texture cellTexture = (row + col) % 2 == 0 ? lightTexture : darkTexture;
				grid[row][col] = new Image(new TextureRegionDrawable(new TextureRegion(cellTexture)));
				table.add(grid[row][col]).size(64, 64);
			}
			table.row();
		}

		tomRow = 0;
		tomCol = 0;
		jerryRow = gridSize - 1;
		jerryCol = gridSize - 1;

		int[] cheeseCols = placeCheeseUsingNQueens(gridSize);
		for (int row = 0; row < cheeseCols.length; row++) {
			int col = cheeseCols[row];
			if ((row == tomRow && col == tomCol) || (row == jerryRow && col == jerryCol)) continue;
			cheesePositions.add(row + "," + col);
		}

		updateGrid();
	}

	private void updateGrid() {
		for (int row = 0; row < gridSize; row++) {
			for (int col = 0; col < gridSize; col++) {
				Texture cellTexture = (row + col) % 2 == 0 ? lightTexture : darkTexture;
				grid[row][col].setDrawable(new TextureRegionDrawable(new TextureRegion(cellTexture)));

				if (cheesePositions.contains(row + "," + col)) {
					grid[row][col].setDrawable(new Image(cheeseTexture).getDrawable());
				}
			}
		}

		grid[tomRow][tomCol].setDrawable(new Image(tomTexture).getDrawable());
		grid[jerryRow][jerryCol].setDrawable(new Image(jerryTexture).getDrawable());
	}

	private int[] placeCheeseUsingNQueens(int n) {
		int[] queens = new int[n];
		solveNQueens(queens, 0);
		return queens;
	}

	private boolean solveNQueens(int[] queens, int row) {
		if (row == queens.length) return true;
		for (int col = 0; col < queens.length; col++) {
			if (isSafe(queens, row, col)) {
				queens[row] = col;
				if (solveNQueens(queens, row + 1)) return true;
			}
		}
		return false;
	}

	private boolean isSafe(int[] queens, int row, int col) {
		for (int i = 0; i < row; i++) {
			int otherCol = queens[i];
			if (otherCol == col || Math.abs(otherCol - col) == Math.abs(i - row)) return false;
		}
		return true;
	}

	private void handleInput() {
		if (gameOver) return;

		int dx = 0, dy = 0;

		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) dy = -1;
		else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) dy = 1;
		else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) dx = -1;
		else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) dx = 1;

		if (dx == 0 && dy == 0) return;

		if (character.equalsIgnoreCase("tom")) {
			moveTom(tomRow + dy, tomCol + dx);
		} else {
			moveJerry(jerryRow + dy, jerryCol + dx);
		}
	}

	private void moveTom(int newRow, int newCol) {
		if (isInBounds(newRow, newCol)) {
			tomRow = newRow;
			tomCol = newCol;
			checkWinConditions();
			updateGrid();
		}
	}

	private void moveJerry(int newRow, int newCol) {
		if (isInBounds(newRow, newCol)) {
			jerryRow = newRow;
			jerryCol = newCol;
			cheesePositions.remove(jerryRow + "," + jerryCol);
			checkWinConditions();
			updateGrid();
		}
	}

	private boolean isInBounds(int row, int col) {
		return row >= 0 && row < gridSize && col >= 0 && col < gridSize;
	}

	private void checkWinConditions() {
		if (tomRow == jerryRow && tomCol == jerryCol) {
			showGameOverDialog("Tom");
		}
		if (cheesePositions.isEmpty()) {
			showGameOverDialog("Jerry");
		}
	}

	@Override
	public void render(float delta) {
		handleInput();
		Gdx.gl.glClearColor(0.1f, 0.2f, 0.4f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(delta, 1 / 30f));
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
		tomTexture.dispose();
		jerryTexture.dispose();
		cheeseTexture.dispose();
		lightTexture.dispose();
		darkTexture.dispose();
		skin.dispose();
	}
}
