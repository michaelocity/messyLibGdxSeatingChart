package com.mygdx.game.gui;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class StudentAssignment implements Screen, InputProcessor {
    //instance vars
    SeatingChart game;
    OrthographicCamera camera;


    public SpriteBatch batch;

    private Texture hitBoxTexture;
    private Texture background;
    private Vector2 lastMouseClick = new Vector2(-100, -100);
    String[] studentNames;
    int scrollRow = 0;
    int scrollAmmount = 7;
    int row;
    int col;
    int[] yPositions ={630,567,505,440,375,310,250};
    Rectangle[] hitboxes = new Rectangle[scrollAmmount];
    Rectangle[] buttons = new Rectangle[5];
    int specialKeys = 0;

    //constructor
    public StudentAssignment(SeatingChart game, int row, int col) {
        background = new Texture("checklist.jfif");
        this.game = game;
        this.row = row;
        this.col = col;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,800);
        batch = new SpriteBatch();
        game.font.setColor(Color.BLUE);
        studentNames = new String[row*col];
        initStudentNames();
        setupHitboxes();
        Gdx.input.setInputProcessor(this);
        game.font = new BitmapFont();
        game.font.setColor(Color.BLUE);
        game.font.getData().setScale(2f);
    }

    //initalizes every student name to an empty string
    private void initStudentNames()
    {
        for (int i =0; i<studentNames.length; i++)
        {
            studentNames[i] = "";
        }
    }

    //setup hitboxes for text input and buttons
    public void setupHitboxes() {
        hitBoxTexture = new Texture("anime.jpg");
        for(int i = 0; i < hitboxes.length; i++) {
            hitboxes[i] = new Rectangle(265,800-yPositions[i]+10,300,40);
        }

        buttons[0] = new Rectangle(575,580,60,90);    // up arrow
        buttons[1] = new Rectangle(575,370,60,90);   // down arrow
        buttons[2] = new Rectangle(65,0,195,125);   // back button
        buttons[3] = new Rectangle(280,0,175,120); // exit button
        buttons[4] = new Rectangle(470,0,155,115);// next button
    }

    //ignore not used
    @Override
    public void show() {

    }

    //render the graphics
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        rectangleRender();
        batch.draw(background,0,0,camera.viewportWidth,camera.viewportHeight);
        renderStudentNames();
        batch.end();
    }

    //render the rectanges for buttons
    private void rectangleRender()
    {
        for(int i = 0; i < scrollAmmount; i++)
        {
            batch.draw(hitBoxTexture, hitboxes[i].x, hitboxes[i].y, hitboxes[i].width, hitboxes[i].height);
        }
        for(int i = 0; i < buttons.length; i++)
            batch.draw(hitBoxTexture, buttons[i].x, buttons[i].y, buttons[i].width, buttons[i].height);
    }

    //render all studnet names
    private void renderStudentNames()
    {
        for(int i = 0;i<scrollAmmount;i++)
        {
            renderName(i);
        }

    }

    //render a name in the correct position on the screen
    private void renderName(int i)
    {
        if(studentNames.length <= i + scrollRow * scrollAmmount)
            game.font.draw(batch, "----------------",265,yPositions[i]-20);
        else if (studentNames[i + scrollRow * scrollAmmount].equals(""))
            game.font.draw(batch, "Enter Student Name",265,yPositions[i]-20);
        else
            game.font.draw(batch, studentNames[i+scrollRow*scrollAmmount],265,yPositions[i]-20);
    }

    //check for button input
    private void buttonChecks() {
        Rectangle buttonClick = new Rectangle(lastMouseClick.x,800-lastMouseClick.y,10, 10);
        if(buttonClick.overlaps(buttons[0]) && scrollRow > 0) {
            scrollRow--;
            System.out.println("@Student Ass: scrollpage num:"+scrollRow);
        } else if(buttonClick.overlaps(buttons[1]) && scrollRow < row*col/7) {
            scrollRow++;
            System.out.println("@Student Ass: scrollpage num:"+scrollRow);
        } else if(buttonClick.overlaps(buttons[2])) {
            game.setScreen(new RowColSetup(game));
            dispose();
        } else if(buttonClick.overlaps(buttons[3])) {
            Gdx.app.exit();
        } else if(buttonClick.overlaps(buttons[4])) {
            game.setScreen(new Classroom(game,studentNames, row, col));
            dispose();
        }
    }

    //ignore used for mobile devices
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

    //dispose of memory intensive items to prevent memory leaks
    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        game.font.dispose();
    }

    //ckeck for key input
    @Override
    public boolean keyDown(int keycode) {
        if(keycode==Input.Keys.BACKSPACE)
        {
            specialKeys = Input.Keys.BACKSPACE;
        }
        else
        {
            specialKeys = 0;
        }
        return true;
    }

    //edit the sudnet name at the given index
    private void editStudentName(char character) {

        System.out.println("@student ass: keytyped:" + character);
        int index = findArrayindex();
        if (index < 0 || index >= studentNames.length) {
            System.out.println("@student ass: index:" + index);
            return;
        }
        else if (specialKeys == Input.Keys.BACKSPACE && studentNames[index].length()>0)
        {
            studentNames[index] = studentNames[index].substring(0,studentNames[index].length()-1);
        }
        else
        {
            studentNames[index] += character;
        }
    }

    //find where in the student array is the current textbox typing to
    private int findArrayindex()
    {
        Rectangle buttonClick = new Rectangle(lastMouseClick.x-5,lastMouseClick.y-5,2,2);
        for(int i =0; i<scrollAmmount;i++)
        {
            if(buttonClick.overlaps(hitboxes[i]))
            {
                System.out.println(i+scrollAmmount+scrollRow);
                return i + scrollRow * scrollAmmount; //returns the line its at plus all previous lines
            }
        }
        return -1;
    }

    //ignore not used
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    //usned to input text to edit student names
    @Override
    public boolean keyTyped(char character) {
        editStudentName(character);
        return true;
    }

    //used to check where the mouse clicked
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("clicked line"+findArrayindex());
        lastMouseClick.x = screenX;
        lastMouseClick.y = screenY;
        buttonChecks();
        return true;
    }

    //ignore not used
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    //scrolling is another way to go through the list of studnets
    @Override
    public boolean scrolled(int amount) {
        scrollRow+=amount;
        if (scrollRow<0)
            scrollRow=0;
        if (scrollRow>studentNames.length/7)
            scrollRow=studentNames.length/7;
        return false;
    }
}
