package com.company;


import java.awt.*;

public class Main {

    public static void main(String[] args) {
        generator gen = new generator(System.currentTimeMillis());

        testGenerator(gen);


        guiManager manager = new guiManager();

        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run() {
                manager.createAndShowGUI();
            }
        });




    }
    // Throws a coin 10 000 times, used to determine quickly if the random number generator is fair
    public static void testGenerator(generator gen)
    {
        int[] lancers = new int[4];

        for(int i = 0; i < 10000; i++)
        {
            //lancers[(int)((gen.nextDouble() % 4))]++;
            //int dice1 = (int) (gen.nextDouble() * 6) % 6;
            lancers[(int)(gen.nextDouble() * 4) % 4]++;
        }

        for(int i = 0; i <4; i++)
        {
            System.out.println("[" + i + "] : " + lancers[i]);
        }


    }
}
