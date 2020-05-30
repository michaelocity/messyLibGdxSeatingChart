package com.mygdx.game.gui;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.math.CheckIfReasonable;
import com.badlogic.gdx.math.Rectangle;

//this screen is used for the teacher to input how many seats are in the class
public class RowColSetup implements Screen, InputProcessor {

    //instance variables
    SeatingChart game;
    OrthographicCamera camera;

    public SpriteBatch batch;
    public BitmapFont font;
    private Texture background;

    //creates hitbox textures
    private Sprite hitbox1;
    private Sprite hitbox2;
    private Sprite hitbox3;
    private Sprite hitbox4;
    private Sprite hitbox5;
    private Sprite hitbox6;
    private Sprite hitbox7;

    private Texture hitBoxTexture;
    Sprite s;

    Vector2 click = new Vector2(-100f,-100f);

    CheckIfReasonable checkIfReasonable = new CheckIfReasonable();
    int rowSize=1;
    int colSize=1;


    //constructor
    public RowColSetup(SeatingChart game) {
        this.game = game;
        background = new Texture("Screen Shot 2020-01-20 at 11.19.29 PM.png");
        createHitBoxes();
        s = new Sprite(hitBoxTexture);


        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,800);

        batch = new SpriteBatch();
        //Use LibGDX's default Arial font.
        font = new BitmapFont();
        font.setColor(Color.RED);
        font.getData().scale(4f);
        game.font.getData().scale(0.1f);
        Gdx.input.setInputProcessor(this);
    }
    //ignore not used
    @Override
    public void show() {

    }
    //render the screen
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        drawHitBoxes();
        batch.draw(background,0,0,camera.viewportWidth,camera.viewportHeight);
        drawRowCol();
        batch.end();

    }
    //draws how many rows and colums currently exist
    private void drawRowCol()
    {
        colSize=checkIfReasonable.colReasonable(colSize);
        rowSize=checkIfReasonable.rowReasonable(rowSize);
        String row = "";
        row+=rowSize;
        font.draw(batch, row, 560, 560);
        String col = "";
        col+=colSize;
        font.draw(batch, col, 560, 275);

    }

    //create the hitboxes for the buttons
    private void createHitBoxes()
    {
        hitBoxTexture = new Texture("anime.jpg");
        hitbox1 = new Sprite(hitBoxTexture,215,110);
        hitbox1.setX(20);
        hitbox1.setY(40);
        hitbox2 = new Sprite(hitBoxTexture,220,110);
        hitbox2.setX(560);
        hitbox2.setY(40);
        hitbox3 = new Sprite(hitBoxTexture,190,110);
        hitbox3.setX(310);
        hitbox3.setY(40);
        hitbox4 = new Sprite(hitBoxTexture,80,120);
        hitbox4.setX(680);
        hitbox4.setY(180);
        hitbox5 = new Sprite(hitBoxTexture,80,120);
        hitbox5.setX(680);
        hitbox5.setY(300);
        hitbox6 = new Sprite(hitBoxTexture,80,120);
        hitbox6.setX(680);
        hitbox6.setY(460);
        hitbox7 = new Sprite(hitBoxTexture,80,120);
        hitbox7.setX(680);
        hitbox7.setY(570);
    }
    //used to dispose of hitboxes and prevent memory leak
    private void disposeHitBoxes()
    {
        hitBoxTexture.dispose();
    }

    //used for debugging the hitboxes
    //do not enable unless you want to see the bounding boxes for the buttons
    private void drawHitBoxes()
    {
        batch.draw(hitbox1,hitbox1.getX(),hitbox1.getY());//back
        batch.draw(hitbox2,hitbox2.getX(),hitbox2.getY());//forward
        batch.draw(hitbox3,hitbox3.getX(),hitbox3.getY());//exit
        batch.draw(hitbox4,hitbox4.getX(),hitbox4.getY());//col down
        batch.draw(hitbox5,hitbox5.getX(),hitbox5.getY());//col up
        batch.draw(hitbox6,hitbox6.getX(),hitbox6.getY());//row down
        batch.draw(hitbox7,hitbox7.getX(),hitbox7.getY());//row up
    }

    //check which button is pressed and respond appropiately
    private void checkButtonPress(int screenX, int screenY)
    {
        screenY=800-screenY;
        Rectangle backHitboxRect = new Rectangle((int)hitbox1.getX(), (int)hitbox1.getY(), (int)hitbox1.getWidth(), (int)hitbox1.getHeight());
        Rectangle nextHitboxRect = new Rectangle((int)hitbox2.getX(), (int)hitbox2.getY(), (int)hitbox2.getWidth(), (int)hitbox2.getHeight());
        Rectangle exitHitboxRect = new Rectangle((int)hitbox3.getX(), (int)hitbox3.getY(), (int)hitbox3.getWidth(), (int)hitbox3.getHeight());
        Rectangle colDownHitboxRect = new Rectangle((int)hitbox4.getX(), (int)hitbox4.getY(), (int)hitbox4.getWidth(), (int)hitbox4.getHeight());
        Rectangle colUptHitboxRect = new Rectangle((int)hitbox5.getX(), (int)hitbox5.getY(), (int)hitbox5.getWidth(), (int)hitbox5.getHeight());
        Rectangle rowDownHitboxRect = new Rectangle((int)hitbox6.getX(), (int)hitbox6.getY(), (int)hitbox6.getWidth(), (int)hitbox6.getHeight());
        Rectangle rowUpHitboxRect = new Rectangle((int)hitbox7.getX(), (int)hitbox7.getY(), (int)hitbox7.getWidth(), (int)hitbox7.getHeight());
        System.out.println(nextHitboxRect);
        Rectangle buttonClick = new Rectangle(screenX,screenY,10,10);

        //example of next button
        if(buttonClick.overlaps(nextHitboxRect))
        {
            game.setScreen(new StudentAssignment(game, rowSize, colSize));
            dispose();
        } else if(buttonClick.overlaps(backHitboxRect)) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        } else if(buttonClick.overlaps(exitHitboxRect)) {
            Gdx.app.exit();
        } else if(buttonClick.overlaps(colDownHitboxRect)) {
            colSize--;
            System.out.println(colSize);
        } else if(buttonClick.overlaps(colUptHitboxRect)) {
            colSize++;
            System.out.println(colSize);
        } else if(buttonClick.overlaps(rowDownHitboxRect)) {
            rowSize--;
            System.out.println(rowSize);
        } else if(buttonClick.overlaps(rowUpHitboxRect)) {
            rowSize++;
            System.out.println(rowSize);
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

    //used to dispose of unessary items to avoid memory leak
    @Override
    public void dispose() {
        disposeHitBoxes();
        font.dispose();
        batch.dispose();
    }

    //ignore not used
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    //used to get mouse input
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        click.x = screenX;
        click.y = screenY;
        checkButtonPress(screenX,screenY);
        return true;
    }

    //ignore not used
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}