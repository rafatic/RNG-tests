package com.company;

import java.security.InvalidParameterException;
import java.util.Arrays;

public class randomWalker {
    private char[][] matrix;
    private int nbSteps;
    private int matrixSize;

    private char mode;
    private generator gen;

    private int currentPositionX, currentPositionY;

    public randomWalker(int nbSteps, int matrixSize, char mode, generator gen)
    {
        this.nbSteps = nbSteps;
        if(matrixSize > 0)
        {
            this.matrixSize = matrixSize;

        }
        else
        {
            throw new InvalidParameterException("matrixSize must be positive");
        }

        matrix = new char[matrixSize][matrixSize];


        for (char[] row: matrix)
            Arrays.fill(row, ' ');

        if(mode == 'C' || mode == 'S' || mode == 'U')
        {
            this.mode = mode;
        }
        else
        {
            throw new InvalidParameterException("Mode must be one of the following :\n  - C : classic\n  - S : sans-retour\n  - U : passage-unique");
        }

        this.gen = gen;

        currentPositionX = matrixSize / 2;
        currentPositionY = 0;
        matrix[currentPositionX][currentPositionY] = '+';


    }

    public char[][] getMatrix()
    {
        return this.matrix;
    }

    public void printMatrix()
    {
        System.out.println("Matrix size : "+ matrixSize+"\nNumber of steps : " + nbSteps);
        System.out.println("Walking mode : " + mode);


        for(int i = 0; i <= matrixSize; i++)
        {
            System.out.print("+");

        }
        System.out.print("\n");

        for(int i = 0; i < matrixSize; i++)
        {
            System.out.print("+");
            for(int j = 0; j < matrixSize; j++)
            {

                System.out.print(matrix[j][i]);
            }
            System.out.print("+\n");
        }

        for(int i = 0; i <= matrixSize; i++)
        {
            System.out.print("+");

        }
        System.out.print("\n");


    }

    public void beginWalk()
    {
        if(mode == 'C')
        {
            classicWalk();
        }
        else if(mode == 'S')
        {
            returnlessWalk();
        }
        else if(mode == 'U')
        {
            selfAvoidingWalk();
        }
        printMatrix();
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
                    currentPositionY--;
                }
                else if(heading == 1 && currentPositionX < matrixSize - 1)
                {
                    currentPositionX++;
                }
                else if(heading == 2 && currentPositionY < matrixSize - 1)
                {
                    currentPositionY++;
                }
                else if(heading == 3 && currentPositionX > 0)
                {
                    currentPositionX--;
                }
                else
                {
                    correctHeading = false;
                }
            }while(!correctHeading);

            matrix[currentPositionX][currentPositionY] = 'x';

        }
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
                    currentPositionY--;
                }
                else if(heading == 1 && currentPositionX < matrixSize - 1 && lastHeading != 3)
                {
                    currentPositionX++;
                }
                else if(heading == 2 && currentPositionY < matrixSize - 1 && lastHeading != 0)
                {
                    currentPositionY++;
                }
                else if(heading == 3 && currentPositionX > 0 && lastHeading != 1)
                {
                    currentPositionX--;
                }
                else
                {
                    correctHeading = false;
                }
            }while(!correctHeading);

            lastHeading = heading;

            matrix[currentPositionX][currentPositionY] = 'x';

        }
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
                    currentPositionY--;
                }
                else if(heading == 1 && currentPositionX < matrixSize - 1 && matrix[currentPositionX + 1][currentPositionY] == ' ')
                {
                    currentPositionX++;
                }
                else if(heading == 2 && currentPositionY < matrixSize - 1 && matrix[currentPositionX][currentPositionY + 1] == ' ')
                {
                    currentPositionY++;
                }
                else if(heading == 3 && currentPositionX > 0 && matrix[currentPositionX - 1][currentPositionY] == ' ')
                {
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
                if(heading == 0 || heading == 2)
                {

                    matrix[currentPositionX][currentPositionY] = '|';
                }
                if(heading == 1 || heading == 3)
                {
                    matrix[currentPositionX][currentPositionY] = '-';
                }
            }





        }

        if(stuck)
        {
            System.out.println("[ERROR] : self-avoiding walk stuck itself on step " + i);
        }
    }


}
