package com.mygdx.game.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//just ta test screen not actually used in game
public class Setup implements Screen {

    //instance variables
    Game game;
    OrthographicCamera camera;

    public SpriteBatch batch;
    public BitmapFont font;

    //constructor
    public Setup(Game game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,480);

        batch = new SpriteBatch();
        //Use LibGDX's default Arial font.
        font = new BitmapFont();
        font.getData().setScale(3.5f);
    }
    //ignore not used
    @Override
    public void show() {

    }
    //render the application
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Welcome to Setup!!! ", 100, 150);
        font.draw(batch, "Tap anywhere to begin!", 100, 100);
        batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new Setup(game));
            dispose();
        }
    }

    //ignore not used
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

    //used to remove unnessary files to prevent memory leaks
    @Override
    public void dispose() {
        font.dispose();
        batch.dispose();
    }
}
