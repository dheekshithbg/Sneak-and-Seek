package com.bgd.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bgd.game.MyGame;

public class MainScreen implements Screen {
	private MyGame parent;
	private Stage stage;
	private String character;
	private int gridSize;
	private Image[][] grid;
	private Texture tomTexture, jerryTexture, cheeseTexture;

	public MainScreen(MyGame parent, String selectedCharacter) {
		this.parent = parent;
		this.character = selectedCharacter;
		this.gridSize = 8; // For example, an 8x8 grid
		stage = new Stage(new ScreenViewport());

		// Load PNG images for Tom, Jerry, and cheese from assets folder
		tomTexture = new Texture(Gdx.files.internal("tom.jpg"));
		jerryTexture = new Texture(Gdx.files.internal("jerry.jpg"));
		cheeseTexture = new Texture(Gdx.files.internal("cheese.png"));

		// Create the grid
		grid = new Image[gridSize][gridSize];

		// Initialize the stage and add the grid layout
		createGrid();
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	private void createGrid() {
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		// Create the grid using a table layout
		for (int row = 0; row < gridSize; row++) {
			for (int col = 0; col < gridSize; col++) {
				grid[row][col] = new Image(); // Empty cell initially
				table.add(grid[row][col]).size(64, 64); // Assuming each cell is 64x64 pixels
			}
			table.row();
		}

		// Place Tom at (0,0) using Tom PNG image
		grid[0][0].setDrawable(new Image(tomTexture).getDrawable());

		// Place Jerry at (n-1,n-1) using Jerry PNG image
		grid[gridSize - 1][gridSize - 1].setDrawable(new Image(jerryTexture).getDrawable());

		// Place cheese using the N-Queens strategy
		int[] cheesePositions = placeCheeseUsingNQueens(gridSize);
		for (int row = 0; row < cheesePositions.length; row++) {
			int col = cheesePositions[row];
			grid[row][col].setDrawable(new Image(cheeseTexture).getDrawable());
		}
	}

	// Implement N-Queens strategy to place cheese on the grid
	private int[] placeCheeseUsingNQueens(int n) {
		int[] queens = new int[n];
		solveNQueens(queens, 0);
		return queens; // Return the column positions of cheese for each row
	}

	private boolean solveNQueens(int[] queens, int row) {
		if (row == queens.length) {
			return true;
		}

		for (int col = 0; col < queens.length; col++) {
			if (isSafe(queens, row, col)) {
				queens[row] = col;
				if (solveNQueens(queens, row + 1)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isSafe(int[] queens, int row, int col) {
		for (int i = 0; i < row; i++) {
			int otherCol = queens[i];
			if (otherCol == col || Math.abs(otherCol - col) == Math.abs(i - row)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
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
	public void hide() {
		// Optionally dispose or clean up the screen
	}

	@Override
	public void dispose() {
		stage.dispose();
		tomTexture.dispose();
		jerryTexture.dispose();
		cheeseTexture.dispose();
	}
}
