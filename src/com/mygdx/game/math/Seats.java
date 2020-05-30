package com.mygdx.game.math;

import com.badlogic.gdx.utils.Array;

public class Seats {
    //instance variables
    private Student[][] StudentList; //students are stored in a 2d array
    private int rows;
    private int cols;

    //takes in an 1d list of student and rows can columns of the classroom and stores a 2d array of the students seated
    public Seats(String[] list, int rows, int cols) {
        this.rows =rows;
        this.cols =cols;
        StudentList = new Student[rows][cols];
        int counter = 0;
        int temp = 0;
        for(int c = 0; c < StudentList[0].length; c++) {
            for(int r = 0; r < StudentList.length; r++) {
                if(counter < list.length) {
                    System.out.println("list Lenght"+list.length+" temp size:"+temp+" r:"+r+" c:"+c);
                    StudentList[r][c] = new Student(list[temp], "A", true);
                    temp++;
                    counter++;
                } else {
                    StudentList[r][c] = new Student("","",false);
                }
            }
        }
    }
    //same as above but now has a boolean for no good reason
    public Seats(String[] list, int rows, int cols, boolean b) {
        this.rows =rows;
        this.cols =cols;
        StudentList = new Student[rows][cols];
        int counter = 0;
        int temp = 0;
        for(int c = 0; c < StudentList[0].length; c++) {
            for(int r = 0; r < StudentList.length; r++) {
                if(counter < list.length) {
                    System.out.println("list Lenght"+list.length+" temp size:"+temp+" r:"+r+" c:"+c);
                    StudentList[r][c] = new Student(list[temp], "A", true);
                    temp++;
                    counter++;
                } else {
                    StudentList[r][c] = new Student("","",false);
                }
            }
        }
    }

    //getters and setters
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    //abbreviate all the names of the students
    public void abbreviateNames() {
        for(int r = 0; r < StudentList.length; r++) {
            for(int c = 0; c < StudentList[0].length; c++) {
                StudentList[r][c].setName(StudentList[r][c].abbreviateName(StudentList[r][c].getName()));
            }
        }
    }

    //getters and setters
    public Student getStudent(int r, int c)
    {
        return StudentList[r][c];
    }
    public void setStudent(int r, int c, Student student)
    {
        StudentList[r][c] = student;
    }

    //revert to the origional names
    public String[] revert() {
        String[] newArr = new String[StudentList.length * StudentList[0].length];
        int i = 0;
        for(int c = 0; c < StudentList[0].length; c++) {
            for(int r = 0; r < StudentList.length; r++) {
                newArr[i] = StudentList[r][c].getName();
                i++;
            }
        }
        return newArr;
    }
}