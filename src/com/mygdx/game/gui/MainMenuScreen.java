package com.mygdx.game.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//thsi shows the main menu
public class MainMenuScreen implements Screen {
    //instance vars
    SeatingChart game;
    OrthographicCamera camera;

    public SpriteBatch batch;
    public BitmapFont font;

    private Texture background;
    private Texture playBtn;
    float timer =0f;

    private String name = "";

    //constructor
    public MainMenuScreen(SeatingChart game) {
        //sets the main menu background/ button picture
        background = new Texture("Screen Shot 2020-01-20 at 11.03.03 PM.png");
        playBtn = new Texture("Screen Shot 2020-01-20 at 11.08.41 PM.png");

        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,800);

        batch = new SpriteBatch();
        //Use LibGDX's default Arial font.
        font = new BitmapFont();
        font.setColor(Color.RED);
        font.getData().scale(3f);
    }
    //ignore not used
    @Override
    public void show() {

    }
    //renders the screen
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        timer+=delta;
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(background,0,0,camera.viewportWidth,camera.viewportHeight);
        batch.draw(playBtn,camera.position.x - playBtn.getWidth() / 2, camera.position.y/2);
        font.draw(batch, name, camera.position.x/2-50, camera.position.y*9/5);

        batch.end();

        if (Gdx.input.isTouched()&&timer>0.5f) {
            game.setScreen(new RowColSetup(game));
            dispose();
        }
    }
    //ignore not used
    @Override
    public void resize(int width, int height) {
        //i still dont know why i couldnt implement resize correctly
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
    //used to remove objects to prevent memory leak
    @Override
    public void dispose() {
        font.dispose();
        batch.dispose();
        background.dispose();
        playBtn.dispose();
        System.out.println("Menu State Disposed");
    }
}

