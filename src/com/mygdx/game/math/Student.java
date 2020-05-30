package com.mygdx.game.math;

public class Student {

    //instance varaibles
    private String name = "";
    private String grade;
    private boolean exists;
    private String abbreviatedName;

    //constructor that takes in name, grade and existance
    public Student(String N, String G, boolean E) {
        if (N == null)
        {

        }
        else {
            name = N;
        }

        grade = G;
        exists = E;
        abbreviatedName = abbreviateName(name);
    }

    //have a shortened name ie. Dave E. For Dave Eddie
    public String abbreviateName(String name) {
        if(name.equals(" "))
        {
            return " ";
        }
        String[] names = name.split(" ");
        String newName = names[0]+" ";
        for (int i = 1; i < names.length; i++) {
                names[i] = names[i].toUpperCase().substring(0, 1) + ". ";
                newName += names[i];
        }
        return newName;
    }

    //setters and getters
    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public boolean isExists() {
        return exists;
    }

    public String getAbbreviatedName() {
        return abbreviatedName;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public boolean doesExist() {
        return exists;
    }

    public String getName() {
        return name;
    }

    public String getGrade() {
        return grade;
    }
}