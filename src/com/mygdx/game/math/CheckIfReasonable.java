package com.mygdx.game.math;
//used to check if the desk arangment is reasonable
public class CheckIfReasonable {
    //checks if the row numbers is reasonable
    public int rowReasonable(int row)
    {
        if (row<1)
        {
            System.out.println("@CheckIfReasonable: row too Small");
            return 1;
        }
        else if (row>9)
        {
            System.out.println("@CheckIfReasonable: row too big");
            return 9;
        }
        return row;
    }
    //check if the column number is reasnonable
    public int colReasonable(int col)
    {
        if (col<1)
        {
            System.out.println("@CheckIfReasonable: col too Small");
            return 1;
        }
        else if (col>8)
        {
            System.out.println("@CheckIfReasonable: col too Big");
            return 8;
        }
        return col;
    }
}
