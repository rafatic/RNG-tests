package com.company;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;

public class randomWalker {
    private char[][] matrix;
    private int nbSteps, nbEffectiveSteps;
    private int matrixSize;

    private int height, width;

    private char mode;
    private generator gen;

    ArrayList<action> actionsHistory;

    private int currentPositionX, currentPositionY;

    public randomWalker(int nbSteps, int width, int height, char mode, generator gen)
    {

        if(height <= 0 || width <= 0)
        {
            throw new InvalidParameterException("The matrix height and width must be GREATER than 0");
        }
        else
        {
            this.height = height;
            this.width = width;
        }

        this.nbSteps = nbSteps;





        if(mode == 'C' || mode == 'S' || mode == 'U')
        {
            this.mode = mode;
        }
        else
        {
            throw new InvalidParameterException("Mode must be one of the following :\n  - C : classic\n  - S : sans-retour\n  - U : passage-unique");
        }

        if(mode == 'C' || mode == 'S')
        {
            actionsHistory = new ArrayList<action>();
        }
        else
        {
            matrix = new char[this.width][this.height];
            for (char[] row: matrix)
            {
                Arrays.fill(row, ' ');
            }
            matrix[currentPositionX][currentPositionY] = '+';
        }

        this.gen = gen;

        currentPositionX = this.width / 2;
        currentPositionY = 0;



    }

    public char[][] getMatrix()
    {
        return this.matrix;
    }

    public ArrayList<action> getActionsHistory()
    {
        return this.actionsHistory;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public void printMatrix()
    {
        System.out.println("Matrix dimensions : "+ height + " : " + width);
        System.out.println("Number of steps : " + nbSteps);
        System.out.println("Walking mode : " + mode);


        for(int i = 0; i <= width; i++)
        {
            System.out.print("+");

        }
        System.out.print("\n");

        for(int i = 0; i < height; i++)
        {
            System.out.print("+");
            for(int j = 0; j < width; j++)
            {

                System.out.print(matrix[j][i]);
            }
            System.out.print("+\n");
        }

        for(int i = 0; i <= width; i++)
        {
            System.out.print("+");

        }
        System.out.print("\n");
    }


    public void printActionHistory()
    {
        System.out.println("Walk Recap");
        for(action a: actionsHistory)
        {
            System.out.println(a.toString());
        }
    }

    public void beginWalk()
    {

        nbEffectiveSteps = 0;
        if(mode == 'C')
        {
            classicWalk();
            printActionHistory();
        }
        else if(mode == 'S')
        {
            returnlessWalk();
            printActionHistory();
        }
        else if(mode == 'U')
        {
            selfAvoidingWalk();
            printMatrix();
        }

    }

    private void classicWalk()
    {
        int heading;
        boolean correctHeading;
        for(int i = 0; i < nbSteps; i++)
        {
            do{
                correctHeading = true;
                heading = (int)(gen.nextDouble() % 4);



                if(heading == 0 && currentPositionY > 0)
                {
                    //matrix[currentPositionX][currentPositionY] = 'U';
                    actionsHistory.add(new action(currentPositionX, currentPositionY, 'U'));
                    currentPositionY--;
                }
                else if(heading == 1 && currentPositionX < width - 1)
                {
                    //matrix[currentPositionX][currentPositionY] = 'R';
                    actionsHistory.add(new action(currentPositionX, currentPositionY, 'R'));
                    currentPositionX++;
                }
                else if(heading == 2 && currentPositionY < height - 1)
                {
                    //matrix[currentPositionX][currentPositionY] = 'D';
                    actionsHistory.add(new action(currentPositionX, currentPositionY, 'D'));
                    currentPositionY++;
                }
                else if(heading == 3 && currentPositionX > 0)
                {
                    //matrix[currentPositionX][currentPositionY] = 'L';
                    actionsHistory.add(new action(currentPositionX, currentPositionY, 'L'));
                    currentPositionX--;
                }
                else
                {
                    correctHeading = false;
                }
            }while(!correctHeading);



        }

        nbEffectiveSteps = nbSteps;

    }

    private void returnlessWalk()
    {
        int heading;
        int lastHeading = -1;
        boolean correctHeading;
        for(int i = 0; i < nbSteps; i++)
        {
            do{
                correctHeading = true;
                heading = (int)(gen.nextDouble() % 4);

                if(heading == 0 && currentPositionY > 0 && lastHeading != 2)
                {
                    actionsHistory.add(new action(currentPositionX, currentPositionY, 'U'));
                    currentPositionY--;
                    //matrix[currentPositionX][currentPositionY] = 'U';
                }
                else if(heading == 1 && currentPositionX < width - 1 && lastHeading != 3)
                {
                    actionsHistory.add(new action(currentPositionX, currentPositionY, 'R'));
                    currentPositionX++;
                    //matrix[currentPositionX][currentPositionY] = 'R';
                }
                else if(heading == 2 && currentPositionY < height - 1 && lastHeading != 0)
                {
                    actionsHistory.add(new action(currentPositionX, currentPositionY, 'D'));
                    currentPositionY++;
                    //matrix[currentPositionX][currentPositionY] = 'D';
                }
                else if(heading == 3 && currentPositionX > 0 && lastHeading != 1)
                {
                    actionsHistory.add(new action(currentPositionX, currentPositionY, 'L'));
                    currentPositionX--;
                    //matrix[currentPositionX][currentPositionY] = 'L';
                }
                else
                {
                    correctHeading = false;
                }
            }while(!correctHeading);

            lastHeading = heading;

        }
        nbEffectiveSteps = nbSteps;

    }

    private void selfAvoidingWalk()
    {
        int heading, i = 0;
        boolean correctHeading;
        boolean[] tries = {true, true, true, true};
        boolean stuck = false;

        while(i < nbSteps && !stuck)
        {
            i++;
            tries = new boolean[]{true, true, true, true};
            do{
                correctHeading = true;
                heading = (int)(gen.nextDouble() % 4);

                if(heading == 0 && currentPositionY > 0 && matrix[currentPositionX][currentPositionY - 1] == ' ')
                {
                    matrix[currentPositionX][currentPositionY] = 'U';
                    currentPositionY--;
                }
                else if(heading == 1 && currentPositionX < width - 1 && matrix[currentPositionX + 1][currentPositionY] == ' ')
                {
                    matrix[currentPositionX][currentPositionY] = 'R';
                    currentPositionX++;
                }
                else if(heading == 2 && currentPositionY < height - 1 && matrix[currentPositionX][currentPositionY + 1] == ' ')
                {
                    matrix[currentPositionX][currentPositionY] = 'D';
                    currentPositionY++;
                }
                else if(heading == 3 && currentPositionX > 0 && matrix[currentPositionX - 1][currentPositionY] == ' ')
                {
                    matrix[currentPositionX][currentPositionY] = 'L';
                    currentPositionX--;
                }
                else
                {
                    tries[heading] = false;
                    correctHeading = false;
                }
                if(!tries[0] && !tries[1] && !tries[2] && !tries[3])
                {
                    stuck = true;
                }
            }while(!correctHeading && !stuck);

            if(!stuck)
            {
                if(matrix[currentPositionX][currentPositionY] != ' ')
                {
                    System.out.println("ERROR SHOULDNT WALK THERE step "+ i);
                }


            }
        }

        if(stuck)
        {
            System.out.println("[ERROR] : self-avoiding walk stuck itself on step " + i);
        }
        nbEffectiveSteps = i;
    }

    public String toString()
    {
        String str = "------------- RESULT ------------- \nMode : ";

        if(mode == 'C')
        {
            str += "Classic Walk";
        }
        else if(mode == 'S')
        {
            str += "ReturnLess Walk";
        }
        else if(mode == 'U')
        {
            str += "Self avoiding Walk";
        }

        str += "\nRequired steps : " + nbSteps + "\n";
        str += "Simulation ended after : " + nbEffectiveSteps + " steps";

        return str;


    }


}
