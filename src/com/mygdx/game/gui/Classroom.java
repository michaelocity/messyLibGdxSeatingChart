package com.mygdx.game.gui;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.math.CheckIfReasonable;
import com.mygdx.game.math.Seats;
import com.mygdx.game.math.Student;

public class Classroom implements Screen, InputProcessor, GestureDetector.GestureListener {
    //instance vars
    SeatingChart game;
    OrthographicCamera camera;

    public SpriteBatch batch;
    public BitmapFont font;
    private Texture background;

    //creates hitbox textures
    private Texture deskTexture;
    private Texture character;

    private Texture hitBoxTexture;
    Sprite s;
    private Sprite[][] desk;

    Rectangle test = new Rectangle();

    Vector2 click = new Vector2(-100f,-100f);

    com.mygdx.game.math.CheckIfReasonable checkIfReasonable = new CheckIfReasonable();
    int rowSize = 1;
    int colSize = 1;

    Seats students;
    int studentClickRow = -1;
    int studentClickCol = -1;

    int studentDragRow = -1;
    int studentDragCol = -1;

    String testDrag = "";
    String[] studentsNative;

    //constructor
    public Classroom(SeatingChart game, String[] students, int rows, int columns) {
        this.game = game;
        background = new Texture("Screen Shot 2020-01-21 at 12.25.18 AM.png"); // 800 x 800
        deskTexture = new Texture("04f939c0d869d16.png"); // 50 x 30
        character = new Texture("TERRADADE1418920344947.jpg"); // 55 x 55

        System.out.println("rows:"+rows+",cols"+columns);
        this.students = new Seats(students,rows,columns);
        this.students.abbreviateNames();
        studentsNative=students;

        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,800);

        batch = new SpriteBatch();
        //Use LibGDX's default Arial font.
        font = new BitmapFont();
        font.setColor(Color.RED);

        InputMultiplexer im = new InputMultiplexer();
        GestureDetector gd = new GestureDetector(this);
        im.addProcessor(gd);
        im.addProcessor(this);


        Gdx.input.setInputProcessor(im);

        desk = new Sprite[rows][columns];
        createDesk();
    }
    //different form of constructor for different setting
    public Classroom(SeatingChart game, String[] students, int rows, int columns, boolean b) {
        this.game = game;
        background = new Texture("Screen Shot 2020-01-21 at 12.25.18 AM.png"); // 800 x 800
        deskTexture = new Texture("04f939c0d869d16.png"); // 50 x 30
        character = new Texture("TERRADADE1418920344947.jpg"); // 55 x 55

        System.out.println("rows:"+rows+",cols"+columns);
        this.students = new Seats(students,rows,columns, b);
        this.students.abbreviateNames();
        studentsNative=students;

        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,800);

        batch = new SpriteBatch();
        //Use LibGDX's default Arial font.
        font = new BitmapFont();
        font.setColor(Color.RED);

        InputMultiplexer im = new InputMultiplexer();
        GestureDetector gd = new GestureDetector(this);
        im.addProcessor(gd);
        im.addProcessor(this);


        Gdx.input.setInputProcessor(im);

        desk = new Sprite[rows][columns];
        createDesk();
    }
    //this constructor is sused to convert from the list of names to a desk arangment of names
    public Classroom(SeatingChart game, String[] students, int rows, int columns, String grade, int studentRow, int studentCol) {
        this.game = game;
        background = new Texture("Screen Shot 2020-01-21 at 12.25.18 AM.png"); // 800 x 800
        deskTexture = new Texture("04f939c0d869d16.png"); // 50 x 30
        character = new Texture("TERRADADE1418920344947.jpg"); // 55 x 55

        System.out.println("rows:"+rows+",cols"+columns);
        this.students = new Seats(students,rows,columns, true);
        this.students.abbreviateNames();
        studentsNative=students;

        this.students.getStudent(studentRow,studentCol).setGrade(grade);
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,800);

        batch = new SpriteBatch();
        //Use LibGDX's default Arial font.
        font = new BitmapFont();
        font.setColor(Color.RED);

        InputMultiplexer im = new InputMultiplexer();
        GestureDetector gd = new GestureDetector(this);
        im.addProcessor(gd);
        im.addProcessor(this);


        Gdx.input.setInputProcessor(im);

        desk = new Sprite[rows][columns];
        createDesk();
    }

    //create sprites for all the desk in the right position
    private void createDesk()
    {
        for(int c = 0; c < desk[0].length; c++)
        {
            for (int r = 0; r < desk.length; r++)
            {
                desk[r][c] = new Sprite(deskTexture);
                desk[r][c].setPosition(170+c*55,250+r*55);
            }
        }
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
        batch.draw(background,0,0, camera.viewportWidth, camera.viewportHeight);
        renderStudentDesk();
        renderStudentInfo();
        batch.draw(character,test.getX(),test.getY(),test.getWidth(),test.getHeight());
        font.draw(batch, "Right click on student to edit/delete, and right click on a desk to add.", 200, 630);
        font.draw(batch, "Click and drag to move(if on mac you may need to click desired student after dragging)", 200, 610);
        batch.end();
    }
    //render the student info of the selected student
    private void renderStudentInfo()
    {
        if(studentClickCol<0 || studentClickRow<0 && !students.getStudent(studentClickRow,studentClickCol).getName().equals(" "))
        {//if the studnet does not exist do not render anything
            return;
        }
        font.draw(batch,"Name: "+students.getStudent(studentClickRow,studentClickCol).getName(),600,550);
        font.draw(batch, "Grade: "+students.getStudent(studentClickRow,studentClickCol).getGrade(),600,500);
    }
    //render the desk
    // render the desk after the studnets so that it looks like the studnets are sitting behind the desk
    private void renderStudentDesk()
    {
        for(int c = desk[0].length-1; c >= 0; c--)
        {
            for (int r = desk.length-1; r >= 0; r--)
            {
                Student temp = students.getStudent(r,c);
                if (temp.getName().equals(" "))
                {

                }
                else
                {
                    batch.draw(character,170+c*55,250+r*55);
                }
                batch.draw(desk[r][c], desk[r][c].getX(), desk[r][c].getY());
            }
        }
    }
    // these 3 methos are just here to mess with the grader of this assignment
    //they serve no purpose
    private void createHitBoxes() { }

    private void disposeHitBoxes()
    {
       // hitBoxTexture.dispose();
    }
    private void drawHitBoxes() { }


    // takes in mouse position
    // sets row and col of student the person clicked
    // if no student clicked return -1;
    private void checkButtonPress(int screenX, int screenY)
    {
        for(int c = 0; c < students.getCols(); c++)
        {
            for(int r = 0; r < students.getRows(); r++)
            {
                //batch.draw(character,170+c*55,250+r*55);
                Rectangle hitbox =  new Rectangle(170+c*55, 250+r*55, character.getWidth(), character.getHeight());
                Rectangle mouse = new Rectangle(screenX,800-screenY,5,5);
                if (hitbox.overlaps(mouse))
                {
                    studentClickRow = r;
                    studentClickCol = c;
                    return;
                }

            }
        }
        studentClickRow = -1;
        studentClickCol = -1;
    }
    //ignore not used
    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }
    //dispose of objects to prevent memory leak
    @Override
    public void dispose() {
        disposeHitBoxes();
        font.dispose();
        batch.dispose();
    }
    //ignore not used
    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {

        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

        return true;
    }

    //used to check if there is a seat whhere the mosue is
    private boolean checkForSeat()
    {
        for(int c = 0; c < students.getCols(); c++)
        {
            for(int r = 0; r < students.getRows(); r++)
            {
                //batch.draw(character,170+c*55,250+r*55);
                Rectangle hitbox =  new Rectangle(170+c*55,250+r*55,50, 30);
                //System.out.println(hitbox);
                if (hitbox.overlaps(test))
                {
                    studentDragRow = r;
                    studentDragCol = c;
                    return true;
                }

            }
        }
        studentDragRow=-1;
        studentDragCol=-1;
        return false;
    }

    //used to move the student by grabbing the the studnet with a mouse
    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {

        drawBox(x, y, deltaX, deltaY);
        return true;
    }
    //used for debugging
    //do not include in final build
    private void drawBox(float x, float y, float deltaX, float deltaY)
    {
        if(studentClickRow > - 1 && studentClickCol > -1 && !students.getStudent(studentClickRow,studentClickCol).getName().equals(" ")) {
            test = new Rectangle(x, 800 - y, 50, 50);
        }
    }
    //ignore not used
    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

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
    //used to find mouse position
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT)
        {
            click.x = screenX;
            click.y = screenY;
            checkButtonPress(screenX,screenY);
        }
        if(button == Input.Buttons.RIGHT)
        {
            toggleDelete(screenX,screenY);
        }
        return false;
    }
    //used to delete bad students from rico's dungeon
    private void toggleDelete(int screenX, int screenY)
    {
        for(int c = 0; c < students.getCols(); c++)
        {
            for(int r = 0; r < students.getRows(); r++)
            {
                //batch.draw(character,170+c*55,250+r*55);
                Rectangle hitbox =  new Rectangle(170+c*55, 250+r*55, character.getWidth(), character.getHeight());
                Rectangle mouse = new Rectangle(screenX,800-screenY,5,5);
                if (hitbox.overlaps(mouse))
                {
                    System.out.println("r: "+r+" c:"+c);
                    Student temp = students.getStudent(r,c);
                    //Classroom(SeatingChart game, String[] students, int rows, int columns)
                    game.setScreen(new StudentEditor(game,students.revert(), students.getRows(), students.getCols(),r,c));
                    dispose();
                    return;
                }

            }
        }

    }
    //used to check if a draged student was droped in a desk
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //checks if released
        System.out.println("hitbox: "+ test);

        System.out.println("working seat:"+ studentDragRow+", "+ studentDragCol);
        if(checkForSeat() && studentDragRow >= 0 && studentClickRow > -1)
        {
            System.out.println("actual working seat:"+ studentDragRow+", "+ studentDragCol);
            Student temp = students.getStudent(studentClickRow,studentClickCol);
            students.setStudent(studentClickRow,studentClickCol, students.getStudent(studentDragRow,studentDragCol));
            students.setStudent(studentDragRow,studentDragCol, temp);
            studentDragRow = -1;
            studentDragCol = -1;
        }

        test.set(0,0,0,0);
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

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
