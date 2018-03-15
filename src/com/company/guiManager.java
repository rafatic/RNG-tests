package com.company;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class guiManager {
    private JButton btn_classic;
    private JButton btn_selfAvoiding;
    private JButton btn_returnless;
    private JTextField txt_nbSteps;
    private JLabel lbl_nbSteps;
    private JPanel pnl_result;
    private JTextArea txtarea_results;
    private JLabel lbl_simulation;
    private JPanel pnl_main;
    private JButton btn_simulate;

    private BufferedImage image;
    private randomWalker walker;
    private generator gen;
    private int resultNbSteps = 0;
    private StringBuilder csvResult;

    public static int LINE_UP = 0;
    public static int LINE_RIGHT = 1;
    public static int LINE_DOWN = 2;
    public static int LINE_LEFT = 3;

    public guiManager()
    {

        image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);


        btn_classic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(txt_nbSteps.getText().isEmpty() || !isDigitsOnly(txt_nbSteps.getText()))
                {
                    txtarea_results.append("\nERROR : Please type the number of steps to execute in the simulation !" + txt_nbSteps.getText());

                }
                else
                {
                    resetSimulationScreen();
                    gen = new generator(System.currentTimeMillis());
                    walker = new randomWalker(Integer.parseInt(txt_nbSteps.getText()), 80,60, 'C', gen);


                    walker.beginWalk();

                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            printFromActionHistory();
                            txtarea_results.setText(walker.toString());
                            txtarea_results.repaint();

                        }
                    });
                }
            }
        });

        btn_returnless.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(txt_nbSteps.getText().isEmpty() || !isDigitsOnly(txt_nbSteps.getText()))
                {
                    txtarea_results.append("\nERROR : Please type the number of steps to execute in the simulation !" + txt_nbSteps.getText());

                }
                else
                {
                    resetSimulationScreen();
                    gen = new generator(System.currentTimeMillis());
                    walker = new randomWalker(Integer.parseInt(txt_nbSteps.getText()), 80,60, 'S', gen);

                    walker.beginWalk();

                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            printFromActionHistory();
                            txtarea_results.setText(walker.toString());
                            txtarea_results.repaint();
                        }
                    });
                }
            }
        });

        btn_selfAvoiding.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(txt_nbSteps.getText().isEmpty() || !isDigitsOnly(txt_nbSteps.getText()))
                {
                    txtarea_results.append("\nERROR : Please type the number of steps to execute in the simulation !" + txt_nbSteps.getText());

                }
                else
                {
                    resetSimulationScreen();
                    gen = new generator(System.currentTimeMillis());
                    walker = new randomWalker(Integer.parseInt(txt_nbSteps.getText()), 80,60, 'U', gen);

                    walker.beginWalk();

                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            printWalkFromMatrix();
                            txtarea_results.setText(walker.toString());
                            txtarea_results.repaint();
                        }
                    });
                }

            }
        });

        btn_simulate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                txtarea_results.setText("Simulation iterations of each walk type for different number of steps.\n");
                resetSimulationScreen();
                csvResult = new StringBuilder();

                PrintWriter writer = null;
                try {
                    writer = new PrintWriter("simulationResult.csv", "UTF-8");
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                randomWalker classicWalker, returnLessWalker, selfAvoidingWalker;


                /*for(int i = 0; i < 50; i++)
                {
                    for(int j = 0; j < 10; j++)
                    {
                        walker = new randomWalker(i, 80, 60, 'C', new generator(System.currentTimeMillis() + j));
                        do {
                            walker.beginWalk();
                        }while(walker.getNbEffectiveSteps() != i);
                        //csvResult.append("C, " + i + ", " + walker.getEndToEndDistance() + "\n");
                        writer.println("C," + i + "," + walker.getEndToEndDistance());

                    }
                }*/
                //System.out.println("\n\n" + csvResult);

                double[] meanClassicLength = new double[100];
                double[] meanReturnLessLength = new double[100];
                double[] meanSelfAvoidingLength = new double[100];
                int nbSelfAvoidingTries;
                for(int i = 0; i < 100; i++)
                {
                    for(int j = 0; j < 100; j++)
                    {
                        classicWalker = new randomWalker(i, 80, 60, 'C', new generator(System.currentTimeMillis() + j));
                        returnLessWalker = new randomWalker(i, 80, 60, 'S', new generator(System.currentTimeMillis() + j + 1));
                        selfAvoidingWalker = new randomWalker(i, 80, 60, 'U', new generator(System.currentTimeMillis() + j + 2));
                        do {
                            classicWalker.beginWalk();
                        }while(classicWalker.getNbEffectiveSteps() != i);

                        do {
                            returnLessWalker.beginWalk();
                        }while(returnLessWalker.getNbEffectiveSteps() != i);

                        nbSelfAvoidingTries = 0;
                        do {
                            selfAvoidingWalker.beginWalk();
                            nbSelfAvoidingTries++;
                        }while(selfAvoidingWalker.getNbEffectiveSteps() != i);

                        meanClassicLength[i] += classicWalker.getEndToEndDistance();
                        meanReturnLessLength[i] += returnLessWalker.getEndToEndDistance();
                        meanSelfAvoidingLength[i] += selfAvoidingWalker.getEndToEndDistance();
                    }

                    meanClassicLength[i] /= 100;
                    meanReturnLessLength[i] /= 100;
                    meanSelfAvoidingLength[i] /= 100;
                }
                writer.println("steps,Classic,ReturnLess,SelfAvoiding");
                for(int i = 0; i < 100; i++)
                {
                    writer.println(i +"," + (meanClassicLength[i] * meanClassicLength[i]) + "," + (meanReturnLessLength[i] * meanReturnLessLength[i]) + "," + (meanSelfAvoidingLength[i] * meanSelfAvoidingLength[i]));
                }
                writer.close();
            }
        });
    }

    public BufferedImage getImage()
    {
        return this.image;
    }

    public void createAndShowGUI()
    {
        JFrame frm = new JFrame("frame");
        frm.setContentPane(pnl_main);

        ImageIcon icon = new ImageIcon(image);
        lbl_simulation.setIcon(icon);

        for(int y = 0; y < 600; y++)
        {
            for(int x = 0; x < 800; x++)
            {
                image.setRGB(x, y, Color.LIGHT_GRAY.getRGB());
            }
        }


        frm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frm.setLocationByPlatform(true);
        frm.pack();
        frm.setVisible(true);
    }

    public void drawLine(int x, int y, int direction) {

        if(direction == LINE_UP)
        {
            for(int i = 0; i < 10; i++)
            {
                image.setRGB(x, y - i, Color.BLUE.getRGB());
            }
        }
        else if(direction == LINE_RIGHT)
        {
            for(int i = 0; i < 10; i++)
            {
                image.setRGB(x + i, y, Color.BLUE.getRGB());
            }
        }
        else if(direction == LINE_DOWN)
        {
            for(int i = 0; i < 10; i++)
            {
                image.setRGB(x, y + i, Color.BLUE.getRGB());
            }
        }
        else if(direction == LINE_LEFT)
        {
            for(int i = 0; i < 10; i++)
            {
                image.setRGB(x - i, y, Color.BLUE.getRGB());
            }
        }

        lbl_simulation.repaint();

    }

    private void printFromActionHistory()
    {
        for(action a: walker.actionsHistory)
        {
            switch (a.getDirection())
            {
                case 'U':
                    drawLine(a.getX() * 10, a.getY() * 10, LINE_UP);
                    break;
                case 'R':
                    drawLine(a.getX() * 10, a.getY() * 10, LINE_RIGHT);
                    break;
                case 'D':
                    drawLine(a.getX() * 10, a.getY() * 10, LINE_DOWN);
                    break;
                case 'L':
                    drawLine(a.getX() * 10, a.getY() * 10, LINE_LEFT);
                    break;
            }

        }

        txtarea_results.setText("");

    }

    private void printWalkFromMatrix()
    {

        for(int y =0; y < walker.getHeight(); y++)
        {
            for(int x = 0; x < walker.getWidth(); x++)
            {
                if(walker.getMatrix()[x][y] == 'U')
                {
                    drawLine(x * 10, y * 10, LINE_UP);
                }
                else if(walker.getMatrix()[x][y] == 'R')
                {
                    drawLine(x * 10, y * 10, LINE_RIGHT);
                }
                else if(walker.getMatrix()[x][y] == 'D')
                {
                    drawLine(x * 10, y * 10, LINE_DOWN);
                }
                else if(walker.getMatrix()[x][y] == 'L')
                {
                    drawLine(x * 10, y * 10, LINE_LEFT);
                }
            }
        }
    }

    private void resetSimulationScreen()
    {
        for(int y =0; y < image.getHeight(); y++)
        {
            for(int x = 0; x < image.getWidth(); x++)
            {
                image.setRGB(x, y, Color.LIGHT_GRAY.getRGB());
            }
        }
        lbl_simulation.repaint();
        lbl_simulation.updateUI();
    }


    private boolean isDigitsOnly(CharSequence str) {
        final int len = str.length();
        for (int i = 0; i < len; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
