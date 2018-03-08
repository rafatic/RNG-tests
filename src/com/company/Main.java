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
