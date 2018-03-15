package com.company;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;

public class randomWalker {
    // Matrix where the path of the selfAvoiding walk is stored.
    // each movement is registered in the according position as U(up) R(right) D(down) L(left)
    private char[][] matrix;

    private int nbSteps, nbEffectiveSteps;

    // vector containing the X Y coordinates of the starting and ending position
    private int[] startPosition, endPosition;

    private int height, width;

    // Defines which walk mode to simulate
    // - C : classic
    // - S : return less
    // - U : unique (return less)
    private char mode;
    private generator gen;

    // Stores the history of every step of a walk.
    // only used for the classic and returnless walks
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

        // Classic and returnLess walks store the history of the walk in an arrayList of actions
        if(mode == 'C' || mode == 'S')
        {
            actionsHistory = new ArrayList<action>();
        }
        // selfAvoiding walks store the history of a walk in a matrix.
        // the matrix is initialized with space values (' ')
        else
        {
            matrix = new char[this.width][this.height];
            for (char[] row: matrix)
            {
                Arrays.fill(row, ' ');
            }
        }

        this.gen = gen;

        // The walk starts at the center top of the grid.
        // this choice is purely arbitrary
        currentPositionX = this.width / 2;
        currentPositionY = 0;

        // Set the startPosition as the current position
        startPosition = new int[2];
        startPosition[0] = currentPositionX;
        startPosition[1] = currentPositionY;

        endPosition = new int[2];

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

    public int getNbEffectiveSteps()
    {
        return this.nbEffectiveSteps;
    }

    public void setGenerator(generator gen)
    {
        this.gen = gen;
    }

    public void setNbSteps(int nbSteps)
    {
        this.nbSteps = nbSteps;
    }

    // Outputs the matrix in the console.
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

    // Prints the actions history in the console
    public void printActionHistory()
    {
        System.out.println("Walk Recap");
        for(action a: actionsHistory)
        {
            System.out.println(a.toString());
        }
    }


    // Starts the walk, the mode used depends of the mode specified in the constructor
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
        // sets the end position as the current position at the end of the simulation
        endPosition[0] = currentPositionX;
        endPosition[1] = currentPositionY;

    }

    private void classicWalk()
    {
        // The classic walk has no specific constraints,
        // however, we need to make sure that the walk does not go out of bounds
        // is the generator causes the walk to go out of bounds, we use another random number.

        // determines in which direction the walk will move
        int heading;
        boolean correctHeading;
        for(int i = 0; i < nbSteps; i++)
        {
            do{
                correctHeading = true;
                heading = (int)(gen.nextDouble() % 4);


                // each time the walk moves, we store the action in the history
                if(heading == 0 && currentPositionY > 0)
                {
                    actionsHistory.add(new action(currentPositionX, currentPositionY, 'U'));
                    currentPositionY--;
                }
                else if(heading == 1 && currentPositionX < width - 1)
                {
                    actionsHistory.add(new action(currentPositionX, currentPositionY, 'R'));
                    currentPositionX++;
                }
                else if(heading == 2 && currentPositionY < height - 1)
                {
                    actionsHistory.add(new action(currentPositionX, currentPositionY, 'D'));
                    currentPositionY++;
                }
                else if(heading == 3 && currentPositionX > 0)
                {
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
        // This walk as only one constraint, don't go directly backwards
        // to avoid that, we store the previous heading, if the current one is its exact opposite,
        // we try with another random number

        // determines in which direction the walk will move
        int heading;

        // determines the previous direction taken by the walk
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
                }
                else if(heading == 1 && currentPositionX < width - 1 && lastHeading != 3)
                {
                    actionsHistory.add(new action(currentPositionX, currentPositionY, 'R'));
                    currentPositionX++;
                }
                else if(heading == 2 && currentPositionY < height - 1 && lastHeading != 0)
                {
                    actionsHistory.add(new action(currentPositionX, currentPositionY, 'D'));
                    currentPositionY++;
                }
                else if(heading == 3 && currentPositionX > 0 && lastHeading != 1)
                {
                    actionsHistory.add(new action(currentPositionX, currentPositionY, 'L'));
                    currentPositionX--;
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

        // In the self avoiding walk, we must never cross our own path.
        // this can lead to scenarios where the walk is blocked.
        // To recognize such a situation, we track which direction has failed during the current step
        // if everything has failed, the walk is stopped.
        boolean[] tries = {true, true, true, true};
        boolean stuck = false;


        matrix = new char[this.width][this.height];
        for (char[] row: matrix)
        {
            Arrays.fill(row, ' ');
        }

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
                // if the try has failed, we store that fact and try with another random number
                else
                {
                    tries[heading] = false;
                    correctHeading = false;
                }
                // if all direction failed for this step, the walk is declared as 'stuck'
                if(!tries[0] && !tries[1] && !tries[2] && !tries[3])
                {
                    stuck = true;
                }
            }while(!correctHeading && !stuck);

            // Used to spot an error where the walk could cross its own path
            if(!stuck)
            {
                if(matrix[currentPositionX][currentPositionY] != ' ')
                {
                    System.out.println("ERROR SHOULDNT WALK THERE step "+ i);
                }


            }
        }

        // Logs the failure of the walk if it was stuck
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
        str += "Simulation ended after : " + nbEffectiveSteps + " steps\n";
        str += "End to end path length : " + getEndToEndDistance() + "\n";

        return str;
    }

    // Return the diagonal distance between the starting point of the walk and its end.
    public int getEndToEndDistance()
    {
        return (Math.abs(endPosition[0] - startPosition[0]) + Math.abs(endPosition[1] - startPosition[1]));
    }



}
