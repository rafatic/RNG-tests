package com.company;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

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

    private BufferedImage image;
    randomWalker walker;
    private generator gen;
    private int resultNbSteps = 0;

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
                System.out.println("CLICKED ON CLASSIC");

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
                System.out.println("CLICKED ON RETURNLESS");

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
                System.out.println("CLICKED ON SELF-AVOIDING");

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
                            //printFromActionHistory();
                            txtarea_results.setText(walker.toString());
                            txtarea_results.repaint();
                        }
                    });
                }

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
