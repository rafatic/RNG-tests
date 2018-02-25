package com.company;

import java.sql.SQLSyntaxErrorException;

public class Main {

    public static void main(String[] args) {
	// write your code here
        generator gen = new generator(12);
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

        testGenerator(gen);


    }

    public static void testGenerator(generator gen)
    {
        int[] lancers = new int[13];

        for(int i = 0; i < 1000; i++)
        {
            lancers[(int)((gen.nextDouble() % 6 + 1 ) + (gen.nextDouble() % 6 + 1))]++;
        }

        for(int i = 2; i <13; i++)
        {
            System.out.println("[" + i + "] : " + lancers[i]);
        }
    }
}
