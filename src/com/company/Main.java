package com.company;


import java.awt.*;

public class Main {

    public static void main(String[] args) {
        guiManager manager = new guiManager();

        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run() {
                manager.createAndShowGUI();
            }
        });




    }

}
