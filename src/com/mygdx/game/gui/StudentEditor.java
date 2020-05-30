package com.mygdx.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class StudentEditor implements Screen, InputProcessor {
    //instance variables
    SeatingChart game;
    OrthographicCamera camera;


    public SpriteBatch batch;

    private Texture hitBoxTexture;
    private Texture background;
    private Vector2 lastMouseClick = new Vector2(-100, -100);
    String[] studentNames;
    String[] editedStudentNames;
    int scrollRow = 0;
    int scrollAmmount = 7;
    int row;
    int col;
    int[] yPositions ={630,567,505,440,375,310,250};
    Rectangle[] lines = new Rectangle[2];
    Rectangle[] buttons = new Rectangle[3];
    int specialKeys = 0;
    int sRow;
    int sCol;
    int sPosition;
    String grade = "";

    //constructor
    public StudentEditor(SeatingChart game, String[] students, int rows, int columns, int studentRow, int studentCol) {
        background = new Texture("studentedit.png");
        this.game = game;
        this.row = rows;
        this.col = columns;
        this.studentNames = students;
        editedStudentNames = studentNames;
        sRow=studentRow;
        sCol=studentCol;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,800);
        batch = new SpriteBatch();
        game.font.setColor(Color.BLUE);
        setupHitboxes();
        Gdx.input.setInputProcessor(this);
        game.font = new BitmapFont();
        game.font.setColor(Color.BLUE);
        game.font.getData().setScale(2f);

        sPosition = studentRow+studentCol * rows;
        System.out.println("student pos:" + sPosition);
    }
    //setup the hitboxes for dragging and dropping students
    public void setupHitboxes() {
        hitBoxTexture = new Texture("anime.jpg");
        lines[0] = new Rectangle(420,550,320,100); // student Name
        lines[1] = new Rectangle(420,360,320,100); // student Grade

        buttons[0] = new Rectangle(30,30,230,120);     // back
        buttons[1] = new Rectangle(270,30,220,110);   // delete
        buttons[2] = new Rectangle(520,30,260,110);  // confirm
    }

    //no use
    @Override
    public void show() {

    }

    //renders the scene
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        rectangleRender();
        batch.draw(background,0,0,camera.viewportWidth,camera.viewportHeight);
        renderName(sPosition);
        renderGrade();
        batch.end();
    }

    //draws the grade at the top right
    private void renderGrade()
    {
        game.font.draw(batch, grade,420,380);
    }

    //used to draw the rectangles for the buttons
    private void rectangleRender()
    {
        for(int i = 0; i < 2; i++)
            batch.draw(hitBoxTexture, lines[i].x, lines[i].y, lines[i].width, lines[i].height);

        for(int i = 0; i < buttons.length; i++)
            batch.draw(hitBoxTexture, buttons[i].x, buttons[i].y, buttons[i].width, buttons[i].height);
    }

    //draws the selected student name
    private void renderName(int i)
    {
             if (studentNames[i + scrollRow * scrollAmmount].equals(" "))
                game.font.draw(batch, "Enter Student Name",420, 590);
            else
                game.font.draw(batch, studentNames[i+scrollRow*scrollAmmount],420,590);
    }

    //checks to see if the player pressed a button and which one
    //I should have used scene 2d and implement in the actor but xD gamer time
    private void buttonChecks() {
        Rectangle buttonClick = new Rectangle(lastMouseClick.x,800-lastMouseClick.y,10, 10);
        if(buttonClick.overlaps(buttons[0])) {
            game.setScreen(new Classroom(game,studentNames, row, col,true));
            dispose();
        }
        else if(buttonClick.overlaps(buttons[1])) {
            editedStudentNames[sPosition] = " ";
            game.setScreen(new Classroom(game, editedStudentNames, row, col,true));
            dispose();
        }
        else if(buttonClick.overlaps(buttons[2])) {
            game.setScreen(new Classroom(game, editedStudentNames, row, col,grade,sRow,sCol));
            dispose();
        }
    }

    //ignore these methods they are used for mobile devices
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
    //gets rid of elements to avoid memory leak
    //dont want java garbage collection messing up my 1000+ fps seating chart
    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        game.font.dispose();
    }

    //used for registering key input
    @Override
    public boolean keyDown(int keycode) {
        if(keycode== Input.Keys.BACKSPACE)
        {
            specialKeys = Input.Keys.BACKSPACE;
        }
        else
        {
            specialKeys = 0;
        }
        return true;
    }

    //used to edit the selected student's name
    private void editStudentName(char character) {

        System.out.println("@student ass: keytyped:" + character);
        int index = findArrayindex();

        System.out.println("s pos:"+index);
        if (specialKeys == Input.Keys.BACKSPACE && grade.length()>0&&index==1)
        {
            grade = grade.substring(0,grade.length()-1);
        }
        else if (index == 1)
        {
            grade += character;
        }
        else if(index == 0)
        {
            editedStudentNames[sPosition] += character;
        }
        else if (specialKeys == Input.Keys.BACKSPACE && editedStudentNames[sPosition].length()>0)
        {//if the key is backspace remove a letter from the string
            editedStudentNames[sPosition] = editedStudentNames[sPosition].substring(0,editedStudentNames[sPosition].length()-1);
        }
        else
        {
            editedStudentNames[sPosition] += character;
        }
    }
    //used to find whick text box was clicked
    private int findArrayindex()
    {
        Rectangle buttonClick = new Rectangle(lastMouseClick.x,lastMouseClick.y,2,2);
        for(int i =0; i<2;i++)
        {
            if(buttonClick.overlaps(lines[i]))
            {

                System.out.println(i+scrollAmmount+scrollRow);
                return i;
            }
        }
        return -1;
    }

    //ignore not used
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    //used to enter letters into the studnet names
    @Override
    public boolean keyTyped(char character) {
        editStudentName(character);
        return true;
    }

    //used to check if any buttons were pressed and update the student name method on which text box was slected
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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

    //scroll throught the list of studnets on screen
    //unessary code since this is one student
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
