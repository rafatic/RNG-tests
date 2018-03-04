package com.company;

import java.sql.SQLSyntaxErrorException;

public class Main {

    public static void main(String[] args) {
	// write your code here
        generator gen = new generator(System.currentTimeMillis());
        int nbHeads = 0;

        /*for(int i = 0; i < 10; i++)
        {
            System.out.println((long)gen.nextDouble());

            if(gen.nextDouble() % 2 == 0.0)
            {
                nbHeads++;
            }

        }

        System.out.println("Prob : " + nbHeads);*/

        //testGenerator(gen);

        randomWalker walker = new randomWalker(100, 30, 'U', gen);

        walker.beginWalk();


    }

    public static void testGenerator(generator gen)
    {
        int[] lancers = new int[4];

        for(int i = 0; i < 10000; i++)
        {
            lancers[(int)((gen.nextDouble() % 4))]++;
        }

        for(int i = 0; i <4; i++)
        {
            System.out.println("[" + i + "] : " + lancers[i]);
        }


    }
}
