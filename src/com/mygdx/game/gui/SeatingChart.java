package com.mygdx.game.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//controlls which screen is active
public class SeatingChart extends Game {
	//instance vars
	public SpriteBatch batch;
	public BitmapFont font;

	//this method acts like a constructor
	public void create() {
		batch = new SpriteBatch();
		Sound mp3Sound = Gdx.audio.newSound(Gdx.files.internal("02 Overworld.mp3"));
		mp3Sound.loop(0.5f);

		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
	}

	//properly render the each sceen
	public void render() {
		super.render();
	}

	//technically not needed since the removal of the game object will crash the game
	//used to prevent memory leaks
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

}
